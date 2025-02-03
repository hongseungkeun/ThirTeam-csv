package com.sparta.csv.domain.movie.repository;

import com.sparta.csv.domain.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
