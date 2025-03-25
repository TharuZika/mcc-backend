//package com.mcc_backend.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mcc_backend.dto.LoginRequest;
//import com.mcc_backend.dto.RegisterRequest;
//import com.mcc_backend.entity.User;
//import com.mcc_backend.service.AuthService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(AuthController.class)
//public class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private LoginRequest loginRequest;
//    private RegisterRequest registerRequest;
//    private User mockUser;
//
//    @BeforeEach
//    void setUp() {
//        loginRequest = new LoginRequest();
//        loginRequest.setUsername("testuser");
//        loginRequest.setPassword("password123");
//
//        registerRequest = new RegisterRequest();
//        registerRequest.setUsername("newuser");
//        registerRequest.setPassword("password123");
//        registerRequest.setEmail("newuser@test.com");
//        registerRequest.setFirstName("New");
//        registerRequest.setLastName("User");
//        registerRequest.setMobileNo("1234567890");
//
//        mockUser = new User();
//        mockUser.setId(1L);
//        mockUser.setUsername("newuser");
//        mockUser.setEmail("newuser@test.com");
//    }
//
//    @Test
//    void loginSuccess() throws Exception {
//        String token = "mock.jwt.token";
//        when(authService.login(any(LoginRequest.class))).thenReturn(token);
//
//        mockMvc.perform(post("/api/auth/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isOk())
//                .andExpect(content().string(token));
//    }
//
//    @Test
//    void registerSuccess() throws Exception {
//        when(authService.register(any(RegisterRequest.class))).thenReturn(mockUser);
//
//        mockMvc.perform(post("/api/auth/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(registerRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value("newuser"))
//                .andExpect(jsonPath("$.email").value("newuser@test.com"));
//    }
//}