package com.sparta.csv.domain.booking.service;

import com.sparta.csv.domain.booking.dto.request.BookingCreateRequest;
import com.sparta.csv.domain.booking.dto.response.TopBookMovie;
import com.sparta.csv.domain.booking.entity.Booking;
import com.sparta.csv.domain.booking.repository.BookingRepository;
import com.sparta.csv.domain.lock.exception.DistributedLockException;
import com.sparta.csv.domain.lock.service.LettuceLockService;
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
@RequiredArgsConstructor
public class BookingService {

    private final UserService userService;
    private final LettuceLockService lettuceLockService;
    private final BookingRegisterService bookingRegisterService;
    private final BookingRepository bookingRepository;

    public Long createBooking(Long userId, Long screeningId, BookingCreateRequest request) {
        User user = userService.findUserById(userId);

        String lockKey = "screening:" + screeningId;

        boolean lock = lettuceLockService.lock(lockKey);
        if (!lock) {
            throw new DistributedLockException(ErrorCode.LOCK_ACQUISITION_FAILED);
        }

        try {
            return bookingRegisterService.registration(request.seatIds(), screeningId, user);
        } finally {
            lettuceLockService.unlock(lockKey);
        }
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
