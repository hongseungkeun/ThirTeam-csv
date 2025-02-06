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

    public List<Seat> findAllSeatsById(List<Long> seatIds) {
        List<Seat> seats = seatRepository.findAllByIdIn(seatIds);

        if (seats.size() != seatIds.size()) {
            throw new NotFoundException(ErrorCode.SEAT_NOT_FOUND);
        }

        return seats;
    }

    public List<Seat> findAllSeats() {
        return seatRepository.findAll();
    }
}
