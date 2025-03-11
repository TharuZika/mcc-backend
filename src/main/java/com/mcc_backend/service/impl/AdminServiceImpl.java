package com.mcc_backend.service.impl;

import com.mcc_backend.dto.VehicleDto;
import com.mcc_backend.dto.VehicleListResponse;
import com.mcc_backend.entity.Vehicle;
import com.mcc_backend.repository.VehicleRepository;
import com.mcc_backend.service.AdminService;
import com.mcc_backend.service.GoogleCloudStorageService;
import com.mcc_backend.util.CustomCheckedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final VehicleRepository vehicleRepository;
    private final GoogleCloudStorageService googleCloudStorageService;

    public AdminServiceImpl(VehicleRepository vehicleRepository, GoogleCloudStorageService googleCloudStorageService) {
        this.vehicleRepository = vehicleRepository;
        this.googleCloudStorageService = googleCloudStorageService;
    }

    @Override
    @Transactional
    public Vehicle addVehicle(VehicleDto vehicleDto) {
        System.out.println("Vehicle add");
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

    public VehicleListResponse fetchVehiclesWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vehicle> vehiclePage = vehicleRepository.findAll(pageable);

        return new VehicleListResponse(
                vehiclePage.getContent(),
                vehiclePage.getTotalElements(),
                vehiclePage.getNumber(),
                vehiclePage.getTotalPages()
        );
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
        vehicle.setStatus(dto.getStatus());

        System.out.println("Trying to upload vehicle image");

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            try {
                String imageUrl = googleCloudStorageService.uploadVehicleImage(
                    dto.getImage(),
                    dto.getPlateNo(),
                    vehicle.getImgUrl()
                );
                vehicle.setImgUrl(imageUrl);
                System.out.println("uploaded image: "+imageUrl);
            } catch (IOException e) {
                System.out.println(e);
                throw new RuntimeException("Failed to upload vehicle image", e);
            } catch (CustomCheckedException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        }
    }
} 