package com.mcc_backend.dto;

import lombok.Data;

@Data
public class GuestCredentialsDto {
    private String username;
    private String password;
    private String message;

    public GuestCredentialsDto(String username, String password) {
        this.username = username;
        this.password = password;
        this.message = "Please save these credentials for future reference";
    }
} 