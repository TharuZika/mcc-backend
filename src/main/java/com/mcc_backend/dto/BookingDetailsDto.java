package com.mcc_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
public class BookingDetailsDto {
    private String serviceType;
    private String pickupLocation;
    private String dropLocation;

    private LocalDate pickupDate;

    private String pickupTime;
    private String vehicleType;
} 