package com.sparta.csv.domain.movie.popularSearch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/movies/popular-search")
public class PopularSearchController {

    @GetMapping
    public ResponseEntity<Map<String,Long>> getPopularSearches(){
        return null;
    }
}
