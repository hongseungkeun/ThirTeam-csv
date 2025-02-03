package com.sparta.csv.domain.booking.repository;

import com.sparta.csv.domain.booking.entity.BookedSeat;
import com.sparta.csv.domain.booking.entity.BookedSeatId;
import com.sparta.csv.domain.booking.entity.Booking;
import com.sparta.csv.domain.seat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedSeatRepository extends JpaRepository<BookedSeat, BookedSeatId> {

    Boolean existsByBookingAndSeat(Booking booking, Seat seat);
}
