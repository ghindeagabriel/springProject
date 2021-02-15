package com.example.cinema_schedule.repository;

import com.example.cinema_schedule.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
