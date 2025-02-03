package com.sparta.csv.domain.screening.entity;

import com.sparta.csv.domain.movie.entity.Movie;
import com.sparta.csv.domain.theater.entity.Theater;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Table(name = "screenings")
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long screeningId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @Column(nullable = false)
    private LocalDateTime showTime;

    public static Screening newScreening(Movie movie, Theater theater, LocalDateTime showTime) {
        return Screening.builder()
                .movie(movie)
                .theater(theater)
                .showTime(showTime)
                .build();
    }
}
