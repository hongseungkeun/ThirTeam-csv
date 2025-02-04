package com.sparta.csv.domain.booking.bookedSeat.entity;

import com.sparta.csv.domain.booking.entity.Booking;
import com.sparta.csv.domain.seat.entity.Seat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booked_seats")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookedSeat {

    @EmbeddedId
    private BookedSeatId id;

    @ManyToOne
    @MapsId("bookingId")
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @MapsId("seatId")
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Builder
    public BookedSeat(BookedSeatId id, Booking booking, Seat seat) {
        this.id = id;
        this.booking = booking;
        this.seat = seat;
    }
}

