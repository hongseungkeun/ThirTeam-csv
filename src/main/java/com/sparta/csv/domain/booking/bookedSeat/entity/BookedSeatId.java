package com.sparta.csv.domain.booking.bookedSeat.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class BookedSeatId implements Serializable {

    private Long bookingId;
    private Long seatId;

    @Builder
    public BookedSeatId(Long bookingId, Long seatId) {
        this.bookingId = bookingId;
        this.seatId = seatId;
    }
}