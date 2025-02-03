package com.sparta.csv.domain.booking.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class BookedSeatId implements Serializable {

    private Long bookId;
    private Long seatId;

    @Builder
    public BookedSeatId(Long bookId, Long seatId) {
        this.bookId = bookId;
        this.seatId = seatId;
    }
}