package com.mcc_backend.service;


import com.mcc_backend.dto.LoginRequest;
import com.mcc_backend.dto.RegisterRequest;
import com.mcc_backend.entity.User;

public interface AuthService {

    public String login(LoginRequest loginRequest);

    public User register(RegisterRequest registerRequest);

}
