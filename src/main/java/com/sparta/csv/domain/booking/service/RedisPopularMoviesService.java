package com.sparta.csv.domain.booking.service;

import com.sparta.csv.domain.booking.dto.response.TopBookMovie;
import com.sparta.csv.domain.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisPopularMoviesService {
    private final BookingRepository bookingRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY = "popularMovies";

    @Transactional(readOnly = true)
    public List<TopBookMovie> getPopularMovies() {
        Set<ZSetOperations.TypedTuple<Object>> rankedMovies = redisTemplate.opsForZSet()
                .reverseRangeWithScores(CACHE_KEY, 0, 9);

        int rank = 1;
        List<TopBookMovie> movies = new ArrayList<>();

        for (ZSetOperations.TypedTuple<Object> tuple : rankedMovies) {
            Object value = tuple.getValue();
            Double score = tuple.getScore();
            String title = (String) value;

            movies.add(new TopBookMovie(rank++, title, score.longValue()));
        }
        return movies;
    }

    @Scheduled(fixedRate = 300000, initialDelay = 10000)
    @Transactional
    public void refreshTopBookedMovies() {
        redisTemplate.delete(CACHE_KEY);
        List<Object[]> result = bookingRepository.findPopularMovies();
        for (Object[] row : result) {
            String movieName = (String) row[0];
            Long bookingCount = (Long) row[1];
            redisTemplate.opsForZSet().add(CACHE_KEY, movieName, bookingCount);
        }
    }
}
