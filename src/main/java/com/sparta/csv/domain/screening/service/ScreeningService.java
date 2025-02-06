package com.sparta.csv.domain.screening.service;

import com.sparta.csv.domain.movie.entity.Movie;
import com.sparta.csv.domain.movie.service.MovieService;
import com.sparta.csv.domain.screening.dto.request.ScreeningRequest;
import com.sparta.csv.domain.screening.dto.response.ScreeningResponse;
import com.sparta.csv.domain.screening.entity.Screening;
import com.sparta.csv.domain.screening.repository.ScreeningRepository;
import com.sparta.csv.domain.theater.entity.Theater;
import com.sparta.csv.domain.theater.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.sparta.csv.domain.screening.entity.Screening.newScreening;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final TheaterService theaterService;
    private final MovieService movieService;

    @Transactional
    public ScreeningResponse createScreening(Long movieId, ScreeningRequest req) {
        Movie movie = movieService.getMovieById(movieId);
        Theater theater = theaterService.findTheaterById(req.theaterId());
        int seats = theater.getSeats().size();

        Screening screening = newScreening(movie, theater, req.screeningTime(), seats);
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
        return screeningRepository.findByScreeningId(id).orElseThrow();
    }
}
