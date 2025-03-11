package com.mcc_backend.service;

import com.mcc_backend.dto.*;
import com.mcc_backend.entity.Vehicle;

import java.util.List;

public interface AdminService {
    Vehicle addVehicle(VehicleDto vehicleDto);
    Vehicle updateVehicle(VehicleDto vehicleDto);
    void deleteVehicle(Long vehicleId);

    VehicleListResponse fetchVehiclesWithPagination(int page, int pageSize);

    UserListResponse fetchUsersWithPagination(int i, int size);

    BookingsListResponse fetchBookingsWithPagination(int i, int size);

    ResponseDto approveBooking(long bookingId);

    ResponseDto rejectBooking(long bookingId);
}