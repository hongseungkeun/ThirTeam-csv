package com.sparta.csv.domain.booking.repository;

import com.sparta.csv.domain.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
