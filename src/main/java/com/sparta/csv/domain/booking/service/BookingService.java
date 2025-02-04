package com.sparta.csv.domain.booking.service;

import com.sparta.csv.domain.booking.dto.request.BookingCreateRequest;
import com.sparta.csv.domain.booking.entity.BookedSeat;
import com.sparta.csv.domain.booking.entity.Booking;
import com.sparta.csv.domain.booking.repository.BookingRepository;
import com.sparta.csv.domain.screening.entity.Screening;
import com.sparta.csv.domain.screening.service.ScreeningService;
import com.sparta.csv.domain.seat.entity.Seat;
import com.sparta.csv.domain.seat.service.SeatService;
import com.sparta.csv.domain.user.entity.User;
import com.sparta.csv.domain.user.service.UserService;
import com.sparta.csv.global.exception.NotFoundException;
import com.sparta.csv.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingService {

    private final UserService userService;
    private final ScreeningService screeningService;
    private final SeatService seatService;
    private final BookedSeatService bookedSeatService;
    private final BookingRepository bookingRepository;

    @Transactional
    public Long registration(Long userId, Long screeningId, BookingCreateRequest request) {
        User user = userService.findUserById(userId);
        Screening screening = screeningService.findScreeningById(screeningId);

        Booking booking = Booking.builder()
                .screening(screening)
                .user(user)
                .build();

        List<BookedSeat> bookedSeats = request.seatIds().stream()
                .map(seatId -> {
                    Seat seat = seatService.findSeatById(seatId);

                    bookedSeatService.existsByBookingAndSeat(booking, seat);

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

    @Transactional
    public void cancellation(Long userId, Long bookingId) {
        Booking booking = findBookingByIdAndStatusIsCompleted(bookingId);

        userService.checkUserAuthentication(booking.getUser().getUserId(), userId);

        booking.cancel();
    }

    public Booking findBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOOKING_NOT_FOUND));
    }

    private Booking findBookingByIdAndStatusIsCompleted(Long bookingId) {
        return bookingRepository.findByIdAndStatusIsCompleted(bookingId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOOKING_NOT_AVAILABLE));
    }
}
