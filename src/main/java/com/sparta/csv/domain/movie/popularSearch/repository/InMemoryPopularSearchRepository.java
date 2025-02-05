package com.sparta.csv.domain.movie.popularSearch.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class InMemoryPopularSearchRepository implements PopularSearchRepository {

    private final Map<String, Long> searchCountMap = new ConcurrentHashMap<>();

    @Override
    public void incrementCount(String search) {
        searchCountMap.merge(search, 1L, Long::sum);
    }

}
