package com.sparta.csv.domain.booking.bookedSeat.repository;

import com.sparta.csv.domain.booking.bookedSeat.entity.BookedSeat;
import com.sparta.csv.domain.booking.bookedSeat.entity.BookedSeatId;
import com.sparta.csv.domain.booking.entity.Booking;
import com.sparta.csv.domain.seat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookedSeatRepository extends JpaRepository<BookedSeat, BookedSeatId> {

    @Query("SELECT COUNT(bs) > 0 FROM BookedSeat bs " +
            "WHERE bs.booking = :booking " +
            "AND bs.seat = :seat " +
            "AND bs.booking.status = 'COMPLETE'")
    Boolean existsByBookingAndSeat(@Param("booking") Booking booking, @Param("seat") Seat seat);
}
