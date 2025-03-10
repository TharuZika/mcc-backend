package com.mcc_backend.controller;

import com.mcc_backend.dto.VehicleDto;
import com.mcc_backend.entity.Vehicle;
import com.mcc_backend.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/vehicles/add")
    public ResponseEntity<Vehicle> addVehicle(@RequestBody VehicleDto vehicleDto) {
        System.out.println("Request vehicle add");
        Vehicle vehicle = adminService.addVehicle(vehicleDto);
        return ResponseEntity.ok(vehicle);
    }

    @PostMapping("/vehicles/update")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody VehicleDto vehicleDto) {
        Vehicle vehicle = adminService.updateVehicle(vehicleDto);
        return ResponseEntity.ok(vehicle);
    }

    @PostMapping("/vehicles/delete/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        adminService.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }
}
