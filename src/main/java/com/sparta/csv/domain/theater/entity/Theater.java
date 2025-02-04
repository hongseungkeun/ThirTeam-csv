package com.sparta.csv.domain.theater.entity;

import com.sparta.csv.domain.movie.dto.request.CreateMovieRequest;
import com.sparta.csv.domain.movie.entity.Movie;
import com.sparta.csv.domain.theater.dto.request.TheaterCreateRequest;
import jakarta.persistence.*;
import lombok.*;

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

    public Theater(String name) {
        this.name = name;
    }

    public static Theater newTheater(TheaterCreateRequest req) {
        return Theater.builder()
                .name(req.name())
                .build();
    }
}
