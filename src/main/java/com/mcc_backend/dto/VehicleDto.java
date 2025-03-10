package com.mcc_backend.dto;

import com.mcc_backend.entity.enums.VehicleType;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class VehicleDto {
    private Long id;
    private VehicleType type;
    private Integer seats;
    private String model;
    private String plateNo;
    private String make;
    private Integer year;
    private BigDecimal pricePerDay;
    private BigDecimal pricePerKm;
    private boolean isTaxi;
    private boolean isRent;
    private String imgUrl;
    private Character status;
} 