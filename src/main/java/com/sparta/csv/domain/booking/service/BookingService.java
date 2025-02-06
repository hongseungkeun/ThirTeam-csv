package com.sparta.csv.domain.booking.service;

import com.sparta.csv.domain.booking.bookedSeat.entity.BookedSeat;
import com.sparta.csv.domain.booking.dto.request.BookingCreateRequest;
import com.sparta.csv.domain.booking.dto.response.TopBookMovie;
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
import com.sparta.csv.domain.user.service.UserService;
import com.sparta.csv.global.exception.NotFoundException;
import com.sparta.csv.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingService {

    private final UserService userService;
    private final ScreeningService screeningService;
    private final SeatService seatService;
    private final BookingRepository bookingRepository;

    @Transactional
    public Long registration(Long userId, Long screeningId, BookingCreateRequest request) {
        User user = userService.findUserById(userId);
        Screening screening = screeningService.findScreeningById(screeningId);

        Booking booking = Booking.builder()
                .screening(screening)
                .user(user)
                .build();

        Theater theater = screening.getTheater();

        List<Seat> seats = seatService.findAllSeatsById(request.seatIds());

        if (seats.size() != request.seatIds().size()) {
            throw new NotFoundException(ErrorCode.SEAT_NOT_FOUND);
        }

        seats.forEach(seat -> {
            if (!seat.getTheater().equals(theater)) {
                throw new SeatNotInTheaterException(ErrorCode.SEAT_NOT_IN_THEATER);
            }
        });

        if (bookingRepository.existsByScreeningAndBookedSeatsSeat(screening, seats)) {
            throw new DuplicateResourceException(ErrorCode.ALREADY_EXIST_BOOKED_SEAT);
        }

        Booking savedBooking = bookingRepository.save(booking);

        List<BookedSeat> bookedSeats = seats.stream()
                .map(seat -> BookedSeat.builder()
                        .booking(savedBooking)
                        .seat(seat)
                        .build()
                )
                .toList();
        booking.getBookedSeats().addAll(bookedSeats);

        screening.decreaseRemainingSeats(savedBooking.getBookedSeats().size());

        return savedBooking.getId();
    }

    @Transactional
    public void cancellation(Long userId, Long bookingId) {
        Booking booking = findBookingByIdAndStatusIsCompleted(bookingId);

        userService.checkUserAuthentication(booking.getUser().getUserId(), userId);

        booking.cancel();
        booking.getScreening().increaseRemainingSeats(booking.getBookedSeats().size());
    }

    private Booking findBookingByIdAndStatusIsCompleted(Long bookingId) {
        return bookingRepository.findByIdAndStatusIsCompleted(bookingId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOOKING_NOT_AVAILABLE));
    }

    public List<TopBookMovie> getPopularMovies() {
        List<Object[]> result = bookingRepository.findPopularMovies();
        return IntStream.range(0, result.size())
                .mapToObj(i -> new TopBookMovie(
                        i + 1,
                        (String) result.get(i)[0],
                        (Long) result.get(i)[1]
                ))
                .toList();
    }

}
