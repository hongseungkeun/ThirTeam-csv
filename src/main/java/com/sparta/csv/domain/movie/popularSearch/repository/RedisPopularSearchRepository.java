package com.sparta.csv.domain.movie.popularSearch.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RedisPopularSearchRepository implements PopularSearchRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String POPULAR_KEY = "popular_searches";

    @Override
    public void incrementCount(String search) {
        redisTemplate.opsForZSet().incrementScore(POPULAR_KEY, search, 1);
    }

    @Override
    public Map<String, Long> findAll() {
        Set<ZSetOperations.TypedTuple<Object>> result =
                redisTemplate.opsForZSet().reverseRangeWithScores(POPULAR_KEY, 0, 9);

        if (result == null || result.isEmpty()) {
            return new LinkedHashMap<>(); // todo: 404 에러 처리
        }

        return result.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.getValue().toString(),  // 🔹 검색어 (key)
                        tuple -> tuple.getScore().longValue(), // 🔹 검색 횟수 (value)
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

}
