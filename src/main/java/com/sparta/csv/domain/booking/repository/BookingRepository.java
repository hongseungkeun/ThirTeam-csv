package com.sparta.csv.domain.booking.repository;

import com.sparta.csv.domain.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b " +
            "WHERE b.id = :bookingId " +
            "AND b.status = 'COMPLETE'")
    Optional<Booking> findByIdAndStatusIsCompleted(@Param("bookingId") Long bookingId);
}
