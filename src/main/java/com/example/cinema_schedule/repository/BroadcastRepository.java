package com.example.cinema_schedule.repository;

import com.example.cinema_schedule.model.Broadcast;
import com.example.cinema_schedule.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BroadcastRepository extends JpaRepository<Broadcast, Long> {
    @Query("SELECT b FROM Broadcast b WHERE b.cinema = ?1")
    List<Broadcast> findListBroadcastByCinemaId(Cinema cinema);

    @Query("SELECT b FROM Broadcast b WHERE b.id = ?1")
    List<Broadcast> findSingleBroadcastById(Long id);
}
