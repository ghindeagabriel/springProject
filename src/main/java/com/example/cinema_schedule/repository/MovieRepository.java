package com.example.cinema_schedule.repository;

import com.example.cinema_schedule.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m WHERE m.id = ?1")
    Movie findMovieById(Long movie_id);
}
