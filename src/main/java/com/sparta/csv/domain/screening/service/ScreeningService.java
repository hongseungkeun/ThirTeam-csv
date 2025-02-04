package com.sparta.csv.domain.screening.service;

import com.sparta.csv.domain.movie.entity.Movie;
import com.sparta.csv.domain.movie.repository.MovieRepository;
import com.sparta.csv.domain.screening.dto.request.CreateScreeningRequest;
import com.sparta.csv.domain.screening.dto.response.ScreeningResponse;
import com.sparta.csv.domain.screening.entity.Screening;
import com.sparta.csv.domain.screening.repository.ScreeningRepository;
import com.sparta.csv.domain.theater.entity.Theater;
import com.sparta.csv.domain.theater.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.sparta.csv.domain.screening.entity.Screening.newScreening;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;

    public ScreeningResponse createScreening(Long movieId, CreateScreeningRequest req) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        Theater theater = theaterRepository.findById(req.theaterId()).orElseThrow();

        Screening screening = newScreening(movie, theater, req.screeningTime());
        Screening saved = screeningRepository.save(screening);

        return ScreeningResponse.from(saved);
    }

    public List<ScreeningResponse> findAllByDateScreenings(LocalDate date) {
        List<Screening> screenings = screeningRepository.findAllByDateScreenings(date);

        return screenings.stream()
                .map(ScreeningResponse::from)
                .toList();
    }

    public Screening findScreeningById(Long id) {
        return screeningRepository.findById(id).orElseThrow();
    }
}
