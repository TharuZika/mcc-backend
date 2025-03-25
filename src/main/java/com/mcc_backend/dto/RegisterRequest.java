package com.mcc_backend.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private DrivingLicenseDto drivingLicense;
}
