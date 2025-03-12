package com.mcc_backend.controller;

import com.mcc_backend.dto.BookingRequestDto;
import com.mcc_backend.dto.BookingResponseDto;
import com.mcc_backend.dto.BookingsListResponse;
import com.mcc_backend.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto bookingRequest) {
        BookingResponseDto response = bookingService.createBooking(bookingRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<BookingsListResponse> fetchBookings() {
        BookingsListResponse response = bookingService.fetchBookings();
        return ResponseEntity.ok(response);
    }
}
