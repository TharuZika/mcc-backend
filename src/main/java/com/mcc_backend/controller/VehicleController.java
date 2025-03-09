package com.mcc_backend.controller;

import com.mcc_backend.dto.ResponseDto;
import com.mcc_backend.entity.Vehicle;
import com.mcc_backend.service.VehicleService;
import com.mcc_backend.util.CommonConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/taxi")
    public ResponseEntity<ResponseDto> getAllTaxiVehicles() {
        List<Vehicle> taxiVehicles = vehicleService.getAllTaxiVehicles();
        return ResponseEntity.ok(new ResponseDto(
            CommonConstants.STATUS_OK,
            "Taxi vehicles retrieved successfully",
            taxiVehicles
        ));
    }

    @GetMapping("/rental")
    public ResponseEntity<ResponseDto> getAllRentalVehicles() {
        List<Vehicle> rentalVehicles = vehicleService.getAllRentalVehicles();
        return ResponseEntity.ok(new ResponseDto(
            CommonConstants.STATUS_OK,
            "Rental vehicles retrieved successfully",
            rentalVehicles
        ));
    }
} 