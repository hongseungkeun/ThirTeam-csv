package com.sparta.csv.domain.theater.service;

import com.sparta.csv.domain.theater.entity.Theater;
import com.sparta.csv.domain.theater.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;

    public Theater findTheaterById(Long theaterId) {
        return theaterRepository.findById(theaterId).orElseThrow();
    }
}
