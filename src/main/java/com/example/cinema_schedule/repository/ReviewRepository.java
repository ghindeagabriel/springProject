package com.example.cinema_schedule.repository;

import com.example.cinema_schedule.model.Movie;
import com.example.cinema_schedule.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.movie = ?1")
    List<Review> findReviewsByMovie(Movie movie);
}
