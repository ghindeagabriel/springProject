package com.example.cinema_schedule.repository;

import com.example.cinema_schedule.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
