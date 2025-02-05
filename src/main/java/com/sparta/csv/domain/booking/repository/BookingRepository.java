package com.sparta.csv.domain.booking.repository;

import com.sparta.csv.domain.booking.entity.Booking;
import com.sparta.csv.domain.screening.entity.Screening;
import com.sparta.csv.domain.seat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b " +
            "WHERE b.id = :bookingId " +
            "AND b.status = 'COMPLETE'")
    Optional<Booking> findByIdAndStatusIsCompleted(Long bookingId);

    @Query("SELECT COUNT(b) > 0 FROM Booking b " +
            "JOIN b.bookedSeats bs " +
            "JOIN bs.seat s " +
            "WHERE b.screening = :screening " +
            "AND s IN :seats " +
            "AND b.status = 'COMPLETE'")
    boolean existsByScreeningAndBookedSeatsSeat(Screening screening, List<Seat> seats);
}
