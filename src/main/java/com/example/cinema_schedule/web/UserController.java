package com.example.cinema_schedule.web;

import com.example.cinema_schedule.model.*;
import com.example.cinema_schedule.repository.*;
import com.example.cinema_schedule.service.SecurityService;
import com.example.cinema_schedule.service.UserService;
import com.example.cinema_schedule.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private BroadcastRepository broadcastRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(ModelMap model) {
        List<Cinema> lstCinemas = cinemaRepository.findAll();
        List<Broadcast> lstBroadcast = broadcastRepository.findAll();

        model.addAttribute("listCinemas", lstCinemas);
        model.addAttribute("listBroadcasts", lstBroadcast);
        return "welcome";
    }

    @RequestMapping(value = "/searchCinema", params = "search_movie", method = RequestMethod.POST)
    public String search(@ModelAttribute("searchCinemaObj") Cinema cinema, BindingResult bindingResult,
                         @RequestParam("selectList") String selected_cinema,
                         ModelMap model){

        if(bindingResult.hasErrors()){
            return "error";
        }

        model.addAttribute("selected_cinema", selected_cinema);

        List<Cinema> lstCinema = cinemaRepository.findAll();
        model.addAttribute("listCinemas", lstCinema);

        List<Broadcast> lstBroadcast = new ArrayList<>();
        for (Cinema c : lstCinema) {
            if (c.getName().equals(selected_cinema)) {
                lstBroadcast = broadcastRepository.findListBroadcastByCinemaId(c);
                break;
            }
        }

        model.addAttribute("listBroadcasts", lstBroadcast);

        return "welcome";
    }

    @GetMapping("/reservation/{broadcast_id}")
    public ModelAndView reservation(@PathVariable("broadcast_id") Long broadcast_id,
                                    ModelMap model){

        //get the broadcast
        List<Broadcast> broadcastSingleList = broadcastRepository.findSingleBroadcastById(broadcast_id);

        //get the actors
        List<Actor> actorList = new ArrayList<>();
        for (Movie_Actor ma: broadcastSingleList.get(0).getMovie().getMovie_actorList()) {
            actorList.add(ma.getActor());
        }

        //get all the seats associated with that broadcast
        List<Seat> seatList = broadcastSingleList.get(0).getSeatList();

        //get all the seats occupied, looking in bookings
        List<Booking> bookingList = bookingRepository.findBookingsByBroadcastId(broadcastSingleList.get(0));
        List<Seat> availableSeatList = new ArrayList<>();
        boolean isOccupied = false;
        for (Seat s: seatList) {
            isOccupied = false;
            for (Booking b: bookingList) {
                if (s.getSeat_number() == b.getSeat()) {
                    isOccupied = true;
                    break;
                }
            }
            if (!isOccupied) {
                availableSeatList.add(s);
            }
        }

        model.addAttribute("ticket_price", broadcastSingleList.get(0).getPrice().getPrice());
        model.addAttribute("movie_title", broadcastSingleList.get(0).getMovie().getName());
        model.addAttribute("listActors", actorList);
        model.addAttribute("listAvailableSeats", availableSeatList);
        model.addAttribute("nr_seats_available", availableSeatList.size());
        model.addAttribute("broadcast_id", broadcast_id);

        return new ModelAndView("ticket", "reservationObj", new Booking());
    }

    @RequestMapping(value = "/create_reservation/{broadcast_id}", params = "createReservation", method = RequestMethod.POST)
    public ModelAndView createRes(@Valid @ModelAttribute("reservationObj") Booking booking, BindingResult bindingResult,
                                  @PathVariable("broadcast_id") Long broadcast_id,
                                  @RequestParam("availableSeatsList") String selected_seat,
                                  ModelMap model) {

        if(bindingResult.hasErrors()){
            return new ModelAndView("error");
        }

        //get the broadcast
        List<Broadcast> broadcastSingleList = broadcastRepository.findSingleBroadcastById(broadcast_id);

        //create booking
        Integer max = 9999;
        Integer min = 1;
        int random_int = (int)(Math.random() * (max-min+1) + min);
        Booking b = new Booking();
        b.setId(((long) random_int));
        b.setBroadcast(broadcastSingleList.get(0));
        b.setSeat(Integer.parseInt(selected_seat));
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)obj).getUsername();
        b.setUser(userRepository.findByUsername(username));
        bookingRepository.save(b);

        return new ModelAndView("redirect:/welcome");
    }

    @GetMapping("/reserved_tickets")
    public String res_tickets(ModelMap model) {

        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)obj).getUsername();
        User currentUser = userRepository.findByUsername(username);

        List<Booking> myBookings = bookingRepository.findBookingByUser(currentUser);
        model.addAttribute("listBooking", myBookings);

        return "reserved_ticket";
    }

    @RequestMapping(value = "/delete_reservation/{booking_id}", params = "deleteReservation", method = RequestMethod.POST)
    public String deleteRes(@PathVariable("booking_id") Long booking_id,
                            ModelMap model) {

        Booking b = bookingRepository.findBookingById(booking_id);
        bookingRepository.delete(b);

        return "redirect:/reserved_tickets";
    }

    @GetMapping("/review/{movie_id}")
    public String review(@PathVariable("movie_id") String movie_id,
                               ModelMap model) {

        //get movie
        Movie m = movieRepository.findMovieById(Long.parseLong(movie_id));

        //get all reviews for that movie
        List<Review> listMovieReviews = reviewRepository.findReviewsByMovie(m);
        model.addAttribute("listReviews", listMovieReviews);
        model.addAttribute("movie_id", movie_id);

        return "review";
    }

    @GetMapping("/review_creation/{movie_id}")
    public ModelAndView reviewCreation(@PathVariable("movie_id") String movie_id,
                                  ModelMap model) {

        model.addAttribute("movie_id", movie_id);
        return new ModelAndView("review_creation", "reviewObj", new Review());
    }

    @RequestMapping(value = "/create_review/{movie_id}", params = "createReview", method = RequestMethod.POST)
    public ModelAndView createRev(@Valid @ModelAttribute("reviewObj") Review review, BindingResult bindingResult,
                                  @PathVariable("movie_id") String movie_id,
                                  @RequestParam("review") String review_description,
                                  ModelMap model) {

        if(bindingResult.hasErrors()){
            return new ModelAndView("error");
        }

        Movie m = movieRepository.findMovieById(Long.parseLong(movie_id));

        Integer max = 9999;
        Integer min = 1;
        int random_int = (int)(Math.random() * (max-min+1) + min);
        Review r = new Review();
        r.setId((long) random_int);
        r.setMovie(m);
        r.setReview(review_description);
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)obj).getUsername();
        r.setUser(userRepository.findByUsername(username));
        reviewRepository.save(r);

        return new ModelAndView("redirect:/review/{movie_id}");
    }
}
