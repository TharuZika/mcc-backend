package com.mcc_backend.service.impl;

import com.mcc_backend.dto.VehicleDto;
import com.mcc_backend.entity.Vehicle;
import com.mcc_backend.repository.VehicleRepository;
import com.mcc_backend.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AdminServiceImpl implements AdminService {

    private final VehicleRepository vehicleRepository;

    public AdminServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    @Transactional
    public Vehicle addVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        updateVehicleFromDto(vehicle, vehicleDto);
        vehicle.setCreatedAt(LocalDateTime.now());
        vehicle.setUpdatedAt(LocalDateTime.now());
        return vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public Vehicle updateVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleRepository.findById(vehicleDto.getId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        
        updateVehicleFromDto(vehicle, vehicleDto);
        vehicle.setUpdatedAt(LocalDateTime.now());
        return vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public void deleteVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicleRepository.delete(vehicle);
    }

    private void updateVehicleFromDto(Vehicle vehicle, VehicleDto dto) {
        vehicle.setType(dto.getType());
        vehicle.setSeats(dto.getSeats());
        vehicle.setModel(dto.getModel());
        vehicle.setPlateNo(dto.getPlateNo());
        vehicle.setMake(dto.getMake());
        vehicle.setYear(dto.getYear());
        vehicle.setPricePerDay(dto.getPricePerDay());
        vehicle.setPricePerKm(dto.getPricePerKm());
        vehicle.setTaxi(dto.isTaxi());
        vehicle.setRent(dto.isRent());
        vehicle.setImgUrl(dto.getImgUrl());
        vehicle.setStatus(dto.getStatus());
    }
} 