package com.sparta.csv.domain.seat.repository;

import com.sparta.csv.domain.seat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
