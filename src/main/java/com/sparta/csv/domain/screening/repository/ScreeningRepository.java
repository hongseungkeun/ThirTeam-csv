package com.sparta.csv.domain.screening.repository;

import com.sparta.csv.domain.screening.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query("SELECT s FROM Screening s WHERE FUNCTION('DATE', s.showTime) = :date")
    List<Screening> findAllByDateScreenings(LocalDate date);
}
