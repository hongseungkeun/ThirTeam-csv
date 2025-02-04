package com.sparta.csv.domain.theater.controller;

import com.sparta.csv.domain.movie.dto.request.CreateMovieRequest;
import com.sparta.csv.domain.movie.dto.response.MovieResponse;
import com.sparta.csv.domain.theater.dto.request.TheaterCreateRequest;
import com.sparta.csv.domain.theater.dto.response.TheaterResponse;
import com.sparta.csv.domain.theater.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class TheaterAdminController {

    private final TheaterService theaterService;

    @PostMapping("/theater")
    public ResponseEntity<TheaterResponse> createTheater(@RequestBody TheaterCreateRequest req) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(theaterService.createTheater(req));
    }
}
