package com.sparta.csv.domain.booking.service;

import com.sparta.csv.domain.booking.dto.request.BookingCreateRequest;
import com.sparta.csv.domain.movie.dto.request.CreateMovieRequest;
import com.sparta.csv.domain.movie.dto.response.MovieResponse;
import com.sparta.csv.domain.movie.service.MovieService;
import com.sparta.csv.domain.screening.dto.request.ScreeningRequest;
import com.sparta.csv.domain.screening.dto.response.ScreeningResponse;
import com.sparta.csv.domain.screening.service.ScreeningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ScreeningService screeningService;

    private Long screeningId;

    @BeforeEach
    void setUp() {
        CreateMovieRequest createMovieRequest = new CreateMovieRequest(
                "테스트 영화",
                120,
                LocalDate.of(2025, 1, 2),
                "테스트 설명"
        );
        MovieResponse movie = movieService.createMovie(createMovieRequest);

        ScreeningRequest screeningRequest = new ScreeningRequest(1L, LocalDateTime.of(2025, 1, 1, 14, 0));
        ScreeningResponse screening = screeningService.createScreening(movie.id(), screeningRequest);
    }

    @Test
    void 동시성_이슈를_검증할_수_있는_테스트_코드_작성() throws InterruptedException {
        // 예약을 테스트할 Screening, User 및 좌석 ID를 세팅함
        Long userId = 1L;
        Long screeningId = 1L;
        List<Long> seatIds1 = List.of(1L, 2L); // 동일한 좌석 ID로 테스트
        List<Long> seatIds2 = List.of(2L, 3L); // 동일한 좌석 ID로 테스트
        List<Long> seatIds3 = List.of(3L, 1L); // 동일한 좌석 ID로 테스트
        List<Long> seatIds4 = List.of(5L, 4L); // 동일한 좌석 ID로 테스트
        List<Long> seatIds5 = List.of(4L, 2L); // 동일한 좌석 ID로 테스트

        BookingCreateRequest request1 = new BookingCreateRequest(seatIds1);
        BookingCreateRequest request = new BookingCreateRequest(seatIds2);
        BookingCreateRequest request = new BookingCreateRequest(seatIds3);
        BookingCreateRequest request = new BookingCreateRequest(seatIds4);
        BookingCreateRequest request = new BookingCreateRequest(seatIds5);

        // Thread 동시 실행을 위한 설정
        int numberOfThreads = 10; // 동시 실행할 Thread 개수
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CyclicBarrier barrier = new CyclicBarrier(numberOfThreads);

        List<Future<Long>> results = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            results.add(executorService.submit(() -> {
                // 모든 Thread가 준비될 때까지 대기
                barrier.await();

                // registration 메소드 호출
                return bookingService.createBooking(userId, screeningId, request);
            }));
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // 성공적으로 예약된 ID는 하나여야 하며, 나머지는 실패하거나 예외를 반환
        int successfulCount = 0;
        int failedCount = 0;

        for (Future<Long> result : results) {
            try {
                result.get(); // 성공적인 예약
                successfulCount++;
            } catch (ExecutionException e) {
                if (e.getCause() instanceof RuntimeException) {
                    failedCount++;
                }
            }
        }

        // 단 하나의 성공과 나머지 실패를 검증
        assertEquals(1, successfulCount, "성공적으로 예약된 건수는 정확히 1건이어야 합니다.");
        assertEquals(numberOfThreads - 1, failedCount, "실패한 건수는 " + (numberOfThreads - 1) + "건이어야 합니다.");
    }
}
