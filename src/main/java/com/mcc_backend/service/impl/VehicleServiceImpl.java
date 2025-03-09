package com.mcc_backend.service.impl;

import com.mcc_backend.entity.Vehicle;
import com.mcc_backend.repository.VehicleRepository;
import com.mcc_backend.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<Vehicle> getAllTaxiVehicles() {
        return vehicleRepository.findByIsTaxiTrue();
    }

    @Override
    public List<Vehicle> getAllRentalVehicles() {
        return vehicleRepository.findByIsRentTrue();
    }
} 