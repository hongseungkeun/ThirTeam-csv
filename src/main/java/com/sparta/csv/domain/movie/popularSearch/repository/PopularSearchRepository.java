package com.sparta.csv.domain.movie.popularSearch.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;

public interface PopularSearchRepository {
    void incrementCount(String search);
    Map<String, Long> findAll();
}
