package com.mcc_backend.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private int id;
    private String username;
    private String  accessToken;
}
