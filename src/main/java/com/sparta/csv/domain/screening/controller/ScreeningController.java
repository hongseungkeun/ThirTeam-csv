package com.sparta.csv.domain.screening.controller;

import com.sparta.csv.domain.screening.dto.response.ScreeningResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScreeningController {
    private final ScreeningService screeningService;

    @PostMapping("/admin/movies/{movidId}/screening")
    public ResponseEntity<ScreeningResponse> createScreening(@PathVariable Long movidId) {
        return ResponseEntity.ok(screeningService.createScreening(movidId));
    }
}
