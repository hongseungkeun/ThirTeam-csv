package com.sparta.csv.domain.movie.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Table(name="movies")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer runTime;

    @Column(nullable = false)
    private LocalDate release_date;

    @Column(nullable = false)
    private String description;

    @Builder
    public Movie(String title, Integer runTime, LocalDate release_date, String description){
        this.title=title;
        this.runTime=runTime;
        this.release_date=release_date;
        this.description=description;
    }
}
