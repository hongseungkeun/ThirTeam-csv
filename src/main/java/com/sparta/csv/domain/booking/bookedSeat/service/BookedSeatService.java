package com.sparta.csv.domain.booking.bookedSeat.service;

import com.sparta.csv.domain.booking.entity.Booking;
import com.sparta.csv.domain.booking.exception.DuplicateResourceException;
import com.sparta.csv.domain.booking.bookedSeat.repository.BookedSeatRepository;
import com.sparta.csv.domain.seat.entity.Seat;
import com.sparta.csv.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookedSeatService {

    private final BookedSeatRepository bookedSeatRepository;

    public void existsByBookingAndSeat(Booking booking, Seat seat) {
        if (bookedSeatRepository.existsByBookingAndSeat(booking, seat)) {
            throw new DuplicateResourceException(ErrorCode.ALREADY_EXIST_BOOKED_SEAT);
        }
    }
}
