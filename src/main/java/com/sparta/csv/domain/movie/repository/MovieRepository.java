package com.sparta.csv.domain.movie.repository;

import com.sparta.csv.domain.movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("""
    SELECT m 
    FROM Movie m 
    WHERE (:title IS NULL OR m.title LIKE %:title%)
""")
    Page<Movie> findAllByTitle(String title, Pageable pageable);
}
