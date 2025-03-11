package com.mcc_backend.service;

import com.mcc_backend.util.CustomCheckedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GoogleCloudStorageService {

    public String uploadFile(MultipartFile file) throws IOException;

    public String uploadVehicleImage(MultipartFile file, String vehicleNo, String existingImage) throws IOException, CustomCheckedException;

    public String uploadDriverLicense(MultipartFile file, String driverLicenseNo, String existingDrivingImage) throws IOException, CustomCheckedException;
}
