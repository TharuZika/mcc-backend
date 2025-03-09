package com.mcc_backend.dto;

import com.mcc_backend.entity.Vehicle;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class BookingRequestDto {
    private String paymentMethod;
    private String cardToken;
    private BookingDetailsDto bookingDetails;
    private Vehicle selectedVehicle;
    private BigDecimal totalAmount;
    private CustomerDetailsDto customerDetails;
}

@Data
class BookingDetailsDto {
    private String serviceType;
    private String pickupLocation;
    private String dropLocation;
    private String pickupDate;
    private String pickupTime;
    private String vehicleType;
}

@Data
class CustomerDetailsDto {
    private String name;
    private String email;
    private String phone;
} 