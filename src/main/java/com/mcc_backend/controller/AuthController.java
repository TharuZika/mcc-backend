package com.mcc_backend.controller;

import com.mcc_backend.dto.LoginRequest;
import com.mcc_backend.dto.RegisterRequest;
import com.mcc_backend.dto.ResponseDto;
import com.mcc_backend.dto.UserDto;
import com.mcc_backend.entity.User;
import com.mcc_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);
        ResponseEntity<ResponseDto> response = authService.login(loginRequest);
        return response;
    }

    @PostMapping("/admin/login")
    public ResponseEntity<ResponseDto> adminLogin(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);
        ResponseEntity<ResponseDto> response = authService.adminLogin(loginRequest);
        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody RegisterRequest registerRequest) {
        ResponseEntity<ResponseDto> register = authService.register(registerRequest);
        return register;
    }
}
