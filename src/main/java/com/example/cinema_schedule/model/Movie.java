package com.example.cinema_schedule.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Long duration;

    @OneToMany(mappedBy = "movie")
    private List<Movie_Actor> movie_actorList;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviewList;

    @OneToMany(mappedBy = "movie")
    private List<Broadcast> broadcastList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Broadcast> getBroadcastList() {
        return broadcastList;
    }

    public void setBroadcastList(List<Broadcast> broadcastList) {
        this.broadcastList = broadcastList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public List<Movie_Actor> getMovie_actorList() {
        return movie_actorList;
    }

    public void setMovie_actorList(List<Movie_Actor> movie_actorList) {
        this.movie_actorList = movie_actorList;
    }
}
