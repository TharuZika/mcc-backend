package com.mcc_backend.service.impl;

import com.mcc_backend.dto.*;
import com.mcc_backend.entity.Booking;
import com.mcc_backend.entity.Status;
import com.mcc_backend.entity.User;
import com.mcc_backend.entity.Vehicle;
import com.mcc_backend.entity.enums.BookingStatus;
import com.mcc_backend.repository.BookingRepository;
import com.mcc_backend.repository.UserRepository;
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
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final VehicleRepository vehicleRepository;
    private final GoogleCloudStorageService googleCloudStorageService;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public AdminServiceImpl(VehicleRepository vehicleRepository, GoogleCloudStorageService googleCloudStorageService, UserRepository userRepository, BookingRepository bookingRepository) {
        this.vehicleRepository = vehicleRepository;
        this.googleCloudStorageService = googleCloudStorageService;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
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

    @Override
    public UserListResponse fetchUsersWithPagination(int i, int size) {
        Pageable pageable = PageRequest.of(i, size);
        Page<User> userPage = userRepository.findAll(pageable);

        return new UserListResponse(
                userPage.getContent(),
                userPage.getTotalElements(),
                userPage.getNumber(),
                userPage.getTotalPages()
        );
    }

    @Override
    public BookingsListResponse fetchBookingsWithPagination(int i, int size) {
        Pageable pageable = PageRequest.of(i, size);
        Page<Booking> bookingPage = bookingRepository.findAll(pageable);

        return new BookingsListResponse(
                bookingPage.getContent(),
                bookingPage.getTotalElements(),
                bookingPage.getNumber(),
                bookingPage.getTotalPages()
        );
    }

    @Override
    public ResponseDto approveBooking(long bookingId) {
        ResponseDto response = new ResponseDto();
        response.setStatus(200);
        response.setMessage("Booking approved");

        Optional<Booking> existingBooking = bookingRepository.findById(bookingId);

        Status status = new Status();
        status.setId(1L);

        Booking booking = existingBooking.get();
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
        return response;
    }

    public ResponseDto rejectBooking(long bookingId) {
        ResponseDto response = new ResponseDto();
        response.setStatus(200);
        response.setMessage("Booking approved");

        Optional<Booking> existingBooking = bookingRepository.findById(bookingId);

        Status status = new Status();
        status.setId(1L);

        Booking booking = existingBooking.get();
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        return response;
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
        vehicle.setTaxi(true);
        vehicle.setRent(true);
        vehicle.setStatus(dto.getStatus());

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            try {
                String imageUrl = googleCloudStorageService.uploadVehicleImage(
                    dto.getImage(),
                    dto.getPlateNo(),
                    vehicle.getImgUrl()
                );
                vehicle.setImgUrl(imageUrl);
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload vehicle image", e);
            }
        }
    }
} 