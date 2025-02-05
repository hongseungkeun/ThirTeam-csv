package com.sparta.csv.domain.theater.entity;

import com.sparta.csv.domain.movie.dto.request.CreateMovieRequest;
import com.sparta.csv.domain.movie.entity.Movie;
import com.sparta.csv.domain.seat.entity.Seat;
import com.sparta.csv.domain.theater.dto.request.TheaterCreateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theaters")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_id")
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @OneToMany(mappedBy = "theater")
    private List<Seat> seats = new ArrayList<>();

    public Theater(String name) {
        this.name = name;
    }

    public static Theater newTheater(TheaterCreateRequest req, List<Seat> seats) {
        return Theater.builder()
                .name(req.name())
                .seats(seats)
                .build();
    }
}
