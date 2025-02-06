package com.sparta.csv.domain.movie.popularSearch.service;

import com.sparta.csv.domain.movie.popularSearch.dto.PopularSearchResponse;
import com.sparta.csv.domain.movie.popularSearch.repository.PopularSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
public class PopularSearchService {
    private final PopularSearchRepository popularSearchRepository;

    public void incrementSearchCount(String search) {
        popularSearchRepository.incrementCount(search);
    }
    public List<PopularSearchResponse> getPopularSearches() {
        Map<String, Long> searchCountMap = popularSearchRepository.findAll();

        return IntStream.range(0, searchCountMap.size())
                .mapToObj(index -> {
                    var sortedEntries = searchCountMap.entrySet().stream()
                            .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                            .toList();
                    var entry = sortedEntries.get(index);
                    return new PopularSearchResponse(index + 1L, entry.getKey(), entry.getValue());
                })
                .toList();
    }
}
