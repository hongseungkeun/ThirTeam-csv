package com.sparta.csv.domain.seat.service;

import com.sparta.csv.domain.seat.entity.Seat;
import com.sparta.csv.domain.seat.repository.SeatRepository;
import com.sparta.csv.global.exception.NotFoundException;
import com.sparta.csv.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public Seat findSeatById(Long seatId) {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SEAT_NOT_FOUND));
    }

    public List<Seat> findAllSeats() {
        return seatRepository.findAll();
    }
}
