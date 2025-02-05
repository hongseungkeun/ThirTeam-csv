package com.sparta.csv.domain.theater.service;

import com.sparta.csv.domain.movie.dto.response.MovieResponse;
import com.sparta.csv.domain.seat.entity.Seat;
import com.sparta.csv.domain.seat.service.SeatService;
import com.sparta.csv.domain.theater.dto.request.TheaterCreateRequest;
import com.sparta.csv.domain.theater.dto.response.TheaterResponse;
import com.sparta.csv.domain.theater.entity.Theater;
import com.sparta.csv.domain.theater.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sparta.csv.domain.theater.entity.Theater.newTheater;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;
    private final SeatService seatService;

    public TheaterResponse createTheater(TheaterCreateRequest req) {
        List<Seat> seats = seatService.findAllSeats();
        Theater theater = newTheater(req, seats);
        theater = theaterRepository.save(theater);
        return TheaterResponse.from(theater);
    }

    public Theater findTheaterById(Long theaterId) {
        return theaterRepository.findById(theaterId).orElseThrow();
    }
}
