package com.mcc_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class FinanceDto {
    private BigDecimal totalRevenue;
    private Long totalBookings;
    private List<GraphData> graphData;
    private List<OrderHistory> orderHistory;
}
