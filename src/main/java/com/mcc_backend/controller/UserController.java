package com.mcc_backend.controller;

import com.mcc_backend.dto.LoginRequest;
import com.mcc_backend.dto.RegisterRequest;
import com.mcc_backend.dto.UserDto;
import com.mcc_backend.entity.User;
import com.mcc_backend.service.AuthService;
import com.mcc_backend.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthServiceImpl authService;

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody UserDto userDto) {
        System.out.println(userDto.getFirstName());
        return ResponseEntity.ok(userDto.getFirstName());
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        User user = authService.register(registerRequest);
        return ResponseEntity.ok(user);
    }
}
