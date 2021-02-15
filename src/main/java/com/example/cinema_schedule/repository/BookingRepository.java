package com.example.cinema_schedule.repository;

import com.example.cinema_schedule.model.Booking;
import com.example.cinema_schedule.model.Broadcast;
import com.example.cinema_schedule.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.id = ?1")
    Booking findBookingById(Long booking_id);

    @Query("SELECT b FROM Booking b WHERE b.broadcast = ?1")
    List<Booking> findBookingsByBroadcastId(Broadcast broadcast);

    @Query("SELECT b FROM Booking b WHERE b.user = ?1 ORDER BY b.broadcast.cinema.name")
    List<Booking> findBookingByUser(User user);
}
