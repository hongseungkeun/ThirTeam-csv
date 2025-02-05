package com.sparta.csv.domain.seat.service;

import com.sparta.csv.domain.seat.entity.Seat;
import com.sparta.csv.domain.seat.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public List<Seat> findAllSeatsById(List<Long> seatIds) {
        return seatRepository.findAllByIdIn(seatIds);
    }

    public List<Seat> findAllSeats() {
        return seatRepository.findAll();
    }
}
