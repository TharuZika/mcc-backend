package com.mcc_backend.service;

import com.mcc_backend.entity.Vehicle;
import java.util.List;

public interface VehicleService {
    List<Vehicle> getAllTaxiVehicles();
    List<Vehicle> getAllRentalVehicles();
} 