package com.mcc_backend.dto;

import com.mcc_backend.entity.Booking;
import com.mcc_backend.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookingsListResponse {
    private List<Booking> bookings;
    private long totalCount;
    private int currentPage;
    private int totalPages;
}
