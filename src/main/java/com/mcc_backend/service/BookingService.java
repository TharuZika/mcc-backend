package com.mcc_backend.service;

import com.mcc_backend.dto.BookingRequestDto;
import com.mcc_backend.dto.BookingResponseDto;
import com.mcc_backend.dto.BookingsListResponse;

public interface BookingService {
    BookingResponseDto createBooking(BookingRequestDto bookingRequest);

    BookingsListResponse fetchBookings();
} 