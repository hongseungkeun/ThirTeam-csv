package com.sparta.csv.domain.theater.repository;

import com.sparta.csv.domain.theater.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
}
