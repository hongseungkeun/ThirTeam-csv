package com.sparta.csv.domain.seat.service;

import com.sparta.csv.domain.seat.entity.Seat;
import com.sparta.csv.domain.seat.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public Seat findSeatById(Long seatId) {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new );
    }
}
