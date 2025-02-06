package com.sparta.csv.domain.seat.repository;

import com.sparta.csv.domain.seat.entity.Seat;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    @EntityGraph(attributePaths = {"theater"})
    List<Seat> findAllByIdIn(List<Long> seatIds);
}
