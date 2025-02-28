package com.sparta.csv.domain.screening.controller;

import com.sparta.csv.domain.screening.dto.request.ScreeningRequest;
import com.sparta.csv.domain.screening.dto.response.ScreeningResponse;
import com.sparta.csv.domain.screening.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScreeningController {
    private final ScreeningService screeningService;

    @PostMapping("/admin/movies/{movieId}/screening")
    public ResponseEntity<ScreeningResponse> createScreening(
            @PathVariable Long movieId,
            @RequestBody ScreeningRequest req
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(screeningService.createScreening(movieId, req));
    }

    @GetMapping("/movies/screenings")
    public ResponseEntity<List<ScreeningResponse>> findAllByDateScreenings(@RequestParam LocalDate date) {
        return ResponseEntity.ok(screeningService.findAllByDateScreenings(date));
    }
}
