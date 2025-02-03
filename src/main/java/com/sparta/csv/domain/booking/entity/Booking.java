package com.sparta.csv.domain.booking.entity;

import com.sparta.csv.domain.screening.entity.Screening;
import com.sparta.csv.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

    @Column(nullable = false)
    private BookingStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screening_id")
    private Screening screening;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookedSeat> bookedSeats = new ArrayList<>();

    @Builder
    public Booking(
            Long id,
            BookingStatus status,
            User user,
            Screening screening,
            List<BookedSeat> bookedSeats
    ) {
        this.id = id;
        this.status = status;
        this.user = user;
        this.screening = screening;
        this.bookedSeats = bookedSeats;
    }
}
