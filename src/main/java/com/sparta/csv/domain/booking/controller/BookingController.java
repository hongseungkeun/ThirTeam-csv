package com.sparta.csv.domain.booking.controller;

import com.sparta.csv.domain.booking.dto.request.BookingCreateRequest;
import com.sparta.csv.domain.booking.service.BookingService;
import com.sparta.csv.global.util.UriUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/movies/screening")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/{screeningId}/bookings")
    public ResponseEntity<Void> createBooking(
            Long userId,
            @PathVariable Long screeningId,
            @RequestBody @Valid BookingCreateRequest request
    ) {
        final Long bookingId = bookingService.registration(userId, screeningId, request);

        final URI uri = UriUtil.create("/api/movies/screening/bookings/{bookingId}", bookingId);

        return ResponseEntity.created(uri).build();
    }
}
