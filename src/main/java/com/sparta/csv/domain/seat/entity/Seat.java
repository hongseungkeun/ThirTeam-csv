package com.sparta.csv.domain.seat.entity;

import com.sparta.csv.domain.booking.entity.BookedSeat;
import com.sparta.csv.domain.theater.entity.Theater;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seats")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Theater theater;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookedSeat> bookedSeats = new ArrayList<>();

    @Builder
    public Seat(
            Long id,
            String name,
            Integer price,
            Theater theater,
            List<BookedSeat> bookedSeats
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.theater = theater;
        this.bookedSeats = bookedSeats;
    }
}
