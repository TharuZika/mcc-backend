package com.mcc_backend.service;

import com.mcc_backend.dto.LoginRequest;
import com.mcc_backend.dto.RegisterRequest;
import com.mcc_backend.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<ResponseDto> login(LoginRequest loginRequest);

    ResponseEntity<ResponseDto> adminLogin(LoginRequest loginRequest);

    ResponseEntity<ResponseDto> register(RegisterRequest registerRequest);
}
