package com.mcc_backend.entity;

import com.mcc_backend.entity.enums.VehicleType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;

    @Column(nullable = false)
    private String model;

    @Column(name = "plate_no", nullable = false, unique = true)
    private String plateNo;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private Integer year;

    @Column(name = "img_url")
    private String imgUrl;
}
