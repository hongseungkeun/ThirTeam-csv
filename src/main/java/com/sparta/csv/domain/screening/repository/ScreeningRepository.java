package com.sparta.csv.domain.screening.repository;

import com.sparta.csv.domain.screening.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
}
