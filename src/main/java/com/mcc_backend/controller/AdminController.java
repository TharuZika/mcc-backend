package com.mcc_backend.controller;

import com.mcc_backend.dto.*;
import com.mcc_backend.entity.Vehicle;
import com.mcc_backend.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/vehicles")
    public ResponseEntity<VehicleListResponse> listVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        VehicleListResponse response = adminService.fetchVehiclesWithPagination(page - 1, size);
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

    @PostMapping("/vehicles/delete/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        adminService.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<UserListResponse> listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        UserListResponse response = adminService.fetchUsersWithPagination(page - 1, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookings")
    public ResponseEntity<BookingsListResponse> listBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        BookingsListResponse response = adminService.fetchBookingsWithPagination(page - 1, size);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/bookings/approve/${}")
    public ResponseEntity<ResponseDto> approveBookings() {
        ResponseDto response = adminService.approveBooking(1);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookings/reject/${}")
    public ResponseEntity<ResponseDto> rejectBookings() {
        ResponseDto response = adminService.rejectBooking(1);
        return ResponseEntity.ok(response);
    }
}
