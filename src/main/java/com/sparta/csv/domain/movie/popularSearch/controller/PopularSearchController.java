package com.sparta.csv.domain.movie.popularSearch.controller;

import com.sparta.csv.domain.movie.popularSearch.dto.PopularSearchResponse;
import com.sparta.csv.domain.movie.popularSearch.service.PopularSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies/popular-search")
public class PopularSearchController {
    private final PopularSearchService popularSearchService;

    @GetMapping
    public ResponseEntity<List<PopularSearchResponse>> getPopularSearches(){
        return ResponseEntity.ok(popularSearchService.getPopularSearches());
    }
}
