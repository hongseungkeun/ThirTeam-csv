package com.sparta.csv.domain.movie.popularSearch.service;

import com.sparta.csv.domain.movie.popularSearch.repository.PopularSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PopularSearchService {
    private final PopularSearchRepository popularSearchRepository;

    public void incrementSearchCount(String search) {
        popularSearchRepository.incrementCount(search);
    }
}
