package com.sparta.csv.domain.booking.service;

import com.sparta.csv.domain.booking.bookedSeat.entity.BookedSeat;
import com.sparta.csv.domain.booking.entity.Booking;
import com.sparta.csv.domain.booking.exception.DuplicateResourceException;
import com.sparta.csv.domain.booking.exception.SeatNotInTheaterException;
import com.sparta.csv.domain.booking.repository.BookingRepository;
import com.sparta.csv.domain.screening.entity.Screening;
import com.sparta.csv.domain.screening.service.ScreeningService;
import com.sparta.csv.domain.seat.entity.Seat;
import com.sparta.csv.domain.seat.service.SeatService;
import com.sparta.csv.domain.theater.entity.Theater;
import com.sparta.csv.domain.user.entity.User;
import com.sparta.csv.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingRegisterService {

    private final ScreeningService screeningService;
    private final SeatService seatService;
    private final BookingRepository bookingRepository;

    @Transactional
    public Long registration(List<Long> seatIds, Long screeningId, User user) {
        List<Seat> seats = seatService.findAllSeatsById(seatIds);
        Screening screening = screeningService.findScreeningById(screeningId);

        registerValidation(seats, screening);

        return saveBooking(seats, screening, user).getId();
    }

    private void registerValidation(List<Seat> seats, Screening screening) {
        Theater theater = screening.getTheater();

        // 해당 영화가 상영하는 극장과 예매하려는 좌석의 극장이 일치하는 지 검사
        seats.forEach(seat -> {
            if (!seat.getTheater().equals(theater)) {
                throw new SeatNotInTheaterException(ErrorCode.SEAT_NOT_IN_THEATER);
            }
        });

        // 해당 예매하려는 좌석이 이미 예매된 좌석인지 검사
        if (bookingRepository.existsByScreeningAndBookedSeatsSeat(screening, seats)) {
            throw new DuplicateResourceException(ErrorCode.ALREADY_EXIST_BOOKED_SEAT);
        }
    }

    private Booking saveBooking(List<Seat> seats, Screening screening, User user) {
        Booking booking = Booking.builder()
                .screening(screening)
                .user(user)
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        List<BookedSeat> bookedSeats = seats.stream()
                .map(seat -> BookedSeat.builder()
                        .booking(savedBooking)
                        .seat(seat)
                        .build()
                )
                .toList();
        booking.getBookedSeats().addAll(bookedSeats);

        screening.decreaseRemainingSeats(booking.getBookedSeats().size());

        return savedBooking;
    }
}
