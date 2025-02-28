package com.sparta.csv.domain.booking.service;

import com.sparta.csv.domain.booking.dto.request.BookingCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Test
    void 동시성_이슈를_검증할_수_있는_테스트_코드_작성() throws InterruptedException {
        // 예약을 테스트할 Screening, User 및 좌석 ID를 세팅함
        Long userId = 1L;
        Long screeningId = 1L;

        Random random = new Random();

        int numberOfThreads = 100; // 동시 실행할 Thread 개수
        List<BookingCreateRequest> request = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            List<Long> seatIds = new ArrayList<>();
            int count = random.nextInt(5) + 1;

            for (int j = 0; j < count; j++) {
                long seat = random.nextLong(40) + 1L;
                seatIds.add(seat);
            }

            BookingCreateRequest bc = new BookingCreateRequest(seatIds);
            request.add(bc);
        }

        // Thread 동시 실행을 위한 설정
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CyclicBarrier barrier = new CyclicBarrier(numberOfThreads);

        List<Future<Long>> results = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            int finalI = i;
            results.add(executorService.submit(() -> {
                // 모든 Thread가 준비될 때까지 대기
                barrier.await();

                // registration 메소드 호출
                return bookingService.createBooking(userId, screeningId, request.get(finalI));
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
