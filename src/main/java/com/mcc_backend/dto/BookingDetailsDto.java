package com.mcc_backend.dto;

import lombok.Data;

@Data
public class BookingDetailsDto {
    private String serviceType;
    private String pickupLocation;
    private String dropLocation;
    private String pickupDate;
    private String pickupTime;
    private String vehicleType;
} 