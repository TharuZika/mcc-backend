package com.mcc_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GraphData {
    private LocalDateTime date;
    private BigDecimal value;
}
