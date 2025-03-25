package com.mcc_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderHistory {
    private Long orderId;
    private BigDecimal amount;
    private LocalDateTime date;
}