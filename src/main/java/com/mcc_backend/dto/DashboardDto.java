package com.mcc_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class DashboardDto {
    private long totalUsers;
    private long totalBookings;
    private long totalVehicles;
    private BigDecimal totalRevenue;
}
