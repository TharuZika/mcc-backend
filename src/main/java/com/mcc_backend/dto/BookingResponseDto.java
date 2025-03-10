package com.mcc_backend.dto;

import com.mcc_backend.entity.Booking;
import lombok.Data;

@Data
public class BookingResponseDto {
    private Booking booking;
    private GuestCredentialsDto guestCredentials;
    private String message;

    public BookingResponseDto(Booking booking, String message) {
        this.booking = booking;
        this.message = message;
    }

    public BookingResponseDto(Booking booking, GuestCredentialsDto guestCredentials, String message) {
        this.booking = booking;
        this.guestCredentials = guestCredentials;
        this.message = message;
    }
}