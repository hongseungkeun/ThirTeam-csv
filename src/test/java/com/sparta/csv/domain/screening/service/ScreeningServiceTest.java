package com.sparta.csv.domain.screening.service;

import com.sparta.csv.domain.screening.dto.request.ScreeningRequest;
import com.sparta.csv.domain.screening.dto.response.ScreeningResponse;
import com.sparta.csv.domain.screening.entity.Screening;
import com.sparta.csv.domain.movie.entity.Movie;
import com.sparta.csv.domain.theater.entity.Theater;
import com.sparta.csv.domain.screening.repository.ScreeningRepository;
import com.sparta.csv.domain.movie.service.MovieService;
import com.sparta.csv.domain.theater.service.TheaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ScreeningServiceTest {

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private TheaterService theaterService;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private ScreeningService screeningService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 영화상영정보_추가_테스트() {
        // Given
        Long movieId = 1L;
        Long theaterId = 2L;
        ScreeningRequest request = new ScreeningRequest(theaterId, LocalDateTime.of(2023, 11, 10, 14, 0));
        Movie movie = Movie.builder()
                .id(movieId)
                .title("Test Movie")
                .runTime(120)
                .release_date(LocalDate.of(2023, 1, 1))
                .description("Test Movie Description")
                .build();
        Theater theater = new Theater("Test Theater");
        ReflectionTestUtils.setField(theater, "id", theaterId);

        Screening screening = Screening.builder()
                .movie(movie)
                .theater(theater)
                .showTime(request.screeningTime())
                .build();
        ReflectionTestUtils.setField(screening, "screeningId", 1L); // 설정된 ID 사용

        when(movieService.getMovieById(movieId)).thenReturn(movie);
        when(theaterService.findTheaterById(theaterId)).thenReturn(theater);
        when(screeningRepository.save(any(Screening.class))).thenReturn(screening);

        // When
        ScreeningResponse response = screeningService.createScreening(movieId, request);

        // Then
        assertEquals(screening.getScreeningId(), response.theaterId());
        assertEquals(screening.getShowTime(), response.screeningTime());
        verify(movieService, times(1)).getMovieById(movieId);
        verify(theaterService, times(1)).findTheaterById(theaterId);
        verify(screeningRepository, times(1)).save(any(Screening.class));
    }

    @Test
    void 영화상영정보_조회_테스트() {
        // Given
        LocalDate date = LocalDate.now();
        Screening screening1 = Screening.builder()
                .movie(Movie.builder().id(1L).title("Movie 1").build())
                .theater(Theater.builder().name("Theater 1").build())
                .showTime(date.atTime(10, 0))
                .build();
        ReflectionTestUtils.setField(screening1, "screeningId", 1L);

        Screening screening2 = Screening.builder()
                .movie(Movie.builder().id(2L).title("Movie 2").build())
                .theater(Theater.builder().name("Theater 2").build())
                .showTime(date.atTime(14, 0))
                .build();
        ReflectionTestUtils.setField(screening2, "screeningId", 2L);

        List<Screening> screenings = List.of(screening1, screening2);

        when(screeningRepository.findAllByDateScreenings(date)).thenReturn(screenings);

        // When
        List<ScreeningResponse> responses = screeningService.findAllByDateScreenings(date);

        // Then
        assertEquals(2, responses.size());
        assertEquals(screening1.getScreeningId(), responses.get(0).theaterId());
        assertEquals(screening1.getShowTime(), responses.get(0).screeningTime());
        assertEquals(screening2.getScreeningId(), responses.get(1).theaterId());
        assertEquals(screening2.getShowTime(), responses.get(1).screeningTime());
        verify(screeningRepository, times(1)).findAllByDateScreenings(date);
    }
}