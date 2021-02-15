package com.example.cinema_schedule.repository;

import com.example.cinema_schedule.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}
