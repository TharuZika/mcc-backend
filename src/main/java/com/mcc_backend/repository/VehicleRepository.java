package com.mcc_backend.repository;

import com.mcc_backend.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByIsTaxiTrue();
    List<Vehicle> findByIsRentTrue();
} 