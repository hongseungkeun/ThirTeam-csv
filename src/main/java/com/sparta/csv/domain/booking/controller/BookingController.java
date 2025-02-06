package com.sparta.csv.domain.booking.controller;

import com.sparta.csv.domain.booking.dto.request.BookingCreateRequest;
import com.sparta.csv.domain.booking.dto.response.TopBookMovie;
import com.sparta.csv.domain.booking.service.BookingService;
import com.sparta.csv.domain.common.entity.AuthUser;
import com.sparta.csv.global.util.UriUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/movies/screening")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/{screeningId}/bookings")
    public ResponseEntity<Void> createBooking(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long screeningId,
            @RequestBody @Valid BookingCreateRequest request
    ) {
        final Long bookingId = bookingService.createBooking(user.getUserId(), screeningId, request);

        final URI uri = UriUtil.create("/api/movies/screening/bookings/{bookingId}", bookingId);

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/bookings/{bookingId}")
    public ResponseEntity<Void> deleteBooking(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long bookingId
    ) {
        bookingService.cancellation(user.getUserId(), bookingId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/bookings/popular-movies")
    public List<TopBookMovie> getPopularMovies() {
        return bookingService.getPopularMovies();
    }
}
