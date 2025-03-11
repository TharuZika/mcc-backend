package com.mcc_backend.dto;

import com.mcc_backend.entity.Vehicle;
import java.util.List;

public class VehicleListResponse {
    private List<Vehicle> vehicles;
    private long totalCount;
    private int currentPage;
    private int totalPages;

    public VehicleListResponse(List<Vehicle> vehicles, long totalCount, int currentPage, int totalPages) {
        this.vehicles = vehicles;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    // Getters and Setters
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
} 