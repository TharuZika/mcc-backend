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

@Data
class GuestCredentialsDto {
    private String username;
    private String password;
    private String message;

    public GuestCredentialsDto(String username, String password) {
        this.username = username;
        this.password = password;
        this.message = "Please save these credentials for future reference";
    }
} 