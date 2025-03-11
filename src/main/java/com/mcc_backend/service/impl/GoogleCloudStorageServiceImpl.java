package com.mcc_backend.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.mcc_backend.service.GoogleCloudStorageService;
import com.mcc_backend.util.CustomCheckedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class GoogleCloudStorageServiceImpl implements GoogleCloudStorageService {

    private final Storage storage;
    private final String bucketName;
    private final String cloudStorageUrl = "https://storage.googleapis.com/%s/%s";

    public GoogleCloudStorageServiceImpl(
            @Value("${application.gcp.bucket-name}") String bucketName,
            @Value("${application.gcp.credentials.location}") String credentialsPath) throws IOException {
        this.bucketName = bucketName;
        
        // Remove 'classpath:' prefix if present
        String resourcePath = credentialsPath.replace("classpath:", "");
        
        // Load credentials from the JSON file
        InputStream credentialsStream = new ClassPathResource(resourcePath).getInputStream();
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
        
        // Initialize storage with credentials
        this.storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());
        return String.format(cloudStorageUrl, bucketName, fileName);
    }

    @Override
    public String uploadVehicleImage(MultipartFile file, String vehicleNo, String existingImage) throws IOException, CustomCheckedException {
        if (existingImage != null && !existingImage.isEmpty()) {
            try {
                String fileName = existingImage.substring(existingImage.lastIndexOf("/") + 1);
                BlobId blobId = BlobId.of(bucketName, fileName);
                storage.delete(blobId);
            } catch (Exception e) {
                throw new CustomCheckedException("Error deleting file: " + existingImage, e);
            }
        }

        String fileName = "vehicles/" + vehicleNo + "-" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());
        return String.format(cloudStorageUrl, bucketName, fileName);
    }

    @Override
    public String uploadDriverLicense(MultipartFile file, String driverLicenseNo, String existingDrivingImage) throws IOException, CustomCheckedException {
        if (existingDrivingImage != null && !existingDrivingImage.isEmpty()) {
            try {
                String fileName = existingDrivingImage.substring(existingDrivingImage.lastIndexOf("/") + 1);
                BlobId blobId = BlobId.of(bucketName, fileName);
                storage.delete(blobId);
            } catch (Exception e) {
                throw new CustomCheckedException("Error deleting file: " + existingDrivingImage, e);
            }
        }

        String fileName = "dl/" + driverLicenseNo + "-" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());
        return String.format(cloudStorageUrl, bucketName, fileName);
    }
}
