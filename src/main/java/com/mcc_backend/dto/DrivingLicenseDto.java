package com.mcc_backend.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DrivingLicenseDto {
    private String number;
    private Date expiryDate;
    private String imageUrl;
}
