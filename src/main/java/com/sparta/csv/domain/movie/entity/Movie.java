package com.sparta.csv.domain.movie.entity;

import com.sparta.csv.domain.movie.dto.request.CreateMovieRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Table(name = "movies")
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private Integer runTime;

    @Column(nullable = false)
    private LocalDate release_date;

    @Lob
    @Column(nullable = false)
    private String description;


    public static Movie newMovie(CreateMovieRequest req) {
        return Movie.builder()
                .title(req.title())
                .runTime(req.runTime())
                .release_date(req.release_date())
                .description(req.description())
                .build();
    }
}
