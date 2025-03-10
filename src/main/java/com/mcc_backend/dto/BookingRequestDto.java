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
