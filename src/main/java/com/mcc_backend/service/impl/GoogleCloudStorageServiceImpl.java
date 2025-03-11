package com.mcc_backend.service.impl;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.mcc_backend.service.GoogleCloudStorageService;
import com.mcc_backend.util.CustomCheckedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class GoogleCloudStorageServiceImpl implements GoogleCloudStorageService {

    private final Storage storage;
    private final String bucketName = "mcc-host-tharuzika";
    private final String cloudStorageUrl = "https://storage.googleapis.com/%s/%s";

    public GoogleCloudStorageServiceImpl() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

        storage.create(blobInfo, file.getBytes());

        // Make the file publicly accessible (optional)
        storage.get(blobId).toBuilder().setAcl(null).build();

        // Return the public URL
        return String.format(cloudStorageUrl, bucketName, fileName);
    }

    @Override
    public String uploadVehicleImage(MultipartFile file, String vehicleNo, String existingImage) throws IOException, CustomCheckedException {

        if (existingImage != null && !existingImage.isEmpty()) {
            try {
                BlobId blobId = BlobId.of(bucketName, existingImage);
                storage.delete(blobId);
            } catch (Exception e) {
                throw new CustomCheckedException("Error deleting file: " + existingImage, e);
            }
        }

        String fileName = vehicleNo + "-" + file.getOriginalFilename();
        String path = "mcc/vehicles/" + fileName;

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, path)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());
        return String.format(cloudStorageUrl, bucketName, path);
    }

    @Override
    public String uploadDriverLicense(MultipartFile file, String driverLicenseNo, String existingDrivingImage) throws IOException, CustomCheckedException {
        if (existingDrivingImage != null && !existingDrivingImage.isEmpty()) {
            try {
                BlobId blobId = BlobId.of(bucketName, existingDrivingImage);
                storage.delete(blobId);
            } catch (Exception e) {
                throw new CustomCheckedException("Error deleting file: " + existingDrivingImage, e);
            }
        }

        String fileName = driverLicenseNo + "-" + file.getOriginalFilename();
        String path = "mcc/dl/" + fileName;

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, path)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());
        return String.format(cloudStorageUrl, bucketName, path);
    }
}
