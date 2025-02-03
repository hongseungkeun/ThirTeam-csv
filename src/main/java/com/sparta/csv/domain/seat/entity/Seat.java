package com.sparta.csv.domain.seat.entity;

import com.sparta.csv.domain.theater.entity.Theater;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Entity
@Table(name = "seats")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @Column(nullable = false)
    @Size(max = 10)
    private String seatNumber;

    @Column(nullable = false)
    private Integer seatPrice;

    public static Seat newSeat(Theater theater, String seatNumber, Integer seatPrice){
        return Seat.builder()
                .theater(theater)
                .seatNumber(seatNumber)
                .seatPrice(seatPrice)
                .build();
    }
}
