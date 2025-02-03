package com.sparta.csv.domain.booking.service;

import com.sparta.csv.domain.booking.dto.request.BookingCreateRequest;
import com.sparta.csv.domain.booking.entity.BookedSeat;
import com.sparta.csv.domain.booking.entity.Booking;
import com.sparta.csv.domain.booking.repository.BookingRepository;
import com.sparta.csv.domain.screening.entity.Screening;
import com.sparta.csv.domain.seat.entity.Seat;
import com.sparta.csv.domain.seat.service.SeatService;
import com.sparta.csv.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingService {

    private final SeatService seatService;
    private final BookingRepository bookingRepository;

    public Long registration(Long userId, Long screeningId, BookingCreateRequest request) {
        // 유저 필요
        User user = null;

        // 상영 정보 필요
        Screening screening = null;

        Booking booking = Booking.builder()
                .screening(screening)
                .user(user)
                .build();

        List<BookedSeat> bookedSeats = request.seatIds().stream()
                .map(seatId -> {
                    Seat seat = seatService.findSeatById(seatId);

                    // 좌석 예약 중복 처리 로직 필요

                    return BookedSeat.builder()
                            .booking(booking)
                            .seat(seat)
                            .build();
                })
                .toList();

        booking.getBookedSeats().addAll(bookedSeats);

        Booking savedBooking = bookingRepository.save(booking);
        return savedBooking.getId();
    }
}
