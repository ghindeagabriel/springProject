package com.example.cinema_schedule.repository;

import com.example.cinema_schedule.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
