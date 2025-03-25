package com.mcc_backend.controller;

import com.mcc_backend.dto.*;
import com.mcc_backend.entity.Vehicle;
import com.mcc_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/vehicles")
    public ResponseEntity<VehicleListResponse> listVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        VehicleListResponse response = adminService.fetchVehiclesWithPagination(page - 1, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDto> dashboardData() {
        DashboardDto response = adminService.fetchDashboardData();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/finance")
    public ResponseEntity<FinanceDto> financeData() {
        FinanceDto response = adminService.fetchFinanceData();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/vehicles/add")
    public ResponseEntity<Vehicle> addVehicle(@ModelAttribute VehicleDto vehicleDto) {
        Vehicle vehicle = adminService.addVehicle(vehicleDto);
        return ResponseEntity.ok(vehicle);
    }

    @PostMapping("/vehicles/update")
    public ResponseEntity<Vehicle> updateVehicle(@ModelAttribute VehicleDto vehicleDto) {
        Vehicle vehicle = adminService.updateVehicle(vehicleDto);
        return ResponseEntity.ok(vehicle);
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        adminService.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<UserListResponse> listUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        UserListResponse response = adminService.fetchUsersWithPagination(page - 1, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookings")
    public ResponseEntity<BookingsListResponse> listBookings(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        BookingsListResponse response = adminService.fetchBookingsWithPagination(page - 1, size);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/bookings/approve/{id}")
    public ResponseEntity<ResponseDto> approveBookings() {
        ResponseDto response = adminService.approveBooking(1);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookings/reject/{id}")
    public ResponseEntity<ResponseDto> rejectBookings() {
        ResponseDto response = adminService.rejectBooking(1);
        return ResponseEntity.ok(response);
    }
}
