package com.sparta.csv.domain.screening.repository;

import com.sparta.csv.domain.screening.entity.Screening;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query("SELECT s FROM Screening s WHERE FUNCTION('DATE', s.showTime) = :date")
    List<Screening> findAllByDateScreenings(LocalDate date);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {"theater"})
    Optional<Screening> findByScreeningId(Long screenId);
}
