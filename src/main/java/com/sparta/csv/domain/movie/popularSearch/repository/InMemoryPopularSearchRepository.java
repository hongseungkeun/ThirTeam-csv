package com.sparta.csv.domain.movie.popularSearch.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class InMemoryPopularSearchRepository implements PopularSearchRepository {

    private final Map<String, Long> searchCountMap = new ConcurrentHashMap<>();

    @Override
    public void incrementCount(String search) {
        searchCountMap.merge(search, 1L, Long::sum);
    }

    @Override
    public Map<String, Long> findAll() {
        return searchCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

}
