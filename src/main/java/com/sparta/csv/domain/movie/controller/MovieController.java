package com.sparta.csv.domain.movie.controller;

import com.sparta.csv.domain.movie.dto.request.CreateMovieRequest;
import com.sparta.csv.domain.movie.dto.response.MovieResponse;
import com.sparta.csv.domain.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MovieController {
    private final MovieService movieService;

    @PostMapping("/admin/movies")
    public ResponseEntity<MovieResponse> createMovie(@RequestBody CreateMovieRequest req) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movieService.createMovie(req));
    }

    @GetMapping("/movies")
    public ResponseEntity<Page<MovieResponse>> findAllMovies(@RequestParam(required = false) String title,
                                                             Pageable pageable) {
        return ResponseEntity.ok(movieService.findAllMovies(title, pageable));
    }

    @GetMapping("/movies/{movieId}")
    public ResponseEntity<MovieResponse> findMovieById(@PathVariable Long movieId) {
        return ResponseEntity.ok(movieService.findMovieById(movieId));
    }

}
