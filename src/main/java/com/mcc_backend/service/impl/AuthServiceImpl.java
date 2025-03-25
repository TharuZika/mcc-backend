package com.mcc_backend.service.impl;

import com.mcc_backend.config.JwtUtil;
import com.mcc_backend.dto.LoginRequest;
import com.mcc_backend.dto.LoginResponse;
import com.mcc_backend.dto.RegisterRequest;
import com.mcc_backend.dto.ResponseDto;
import com.mcc_backend.entity.Role;
import com.mcc_backend.entity.User;
import com.mcc_backend.repository.RoleRepository;
import com.mcc_backend.repository.UserRepository;
import com.mcc_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ResponseDto> login(LoginRequest loginRequest) {
        ResponseDto responseDto = new ResponseDto();
        
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            if (authentication.isAuthenticated()) {
                Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());
                if (userOpt.isPresent()) {
                    User user = userOpt.get();

                    String token = jwtUtil.generateToken(user);

                    LoginResponse resUser = new LoginResponse();
                    resUser.setId(user.getId());
                    resUser.setAccessToken(token);
                    resUser.setUsername(user.getUsername());
                    
                    responseDto.setStatus(HttpStatus.OK.value());
                    responseDto.setMessage("Login Successful");
                    responseDto.setData(resUser);
                    return new ResponseEntity<>(responseDto, HttpStatus.OK);
                }
            }
            
            responseDto.setStatus(HttpStatus.UNAUTHORIZED.value());
            responseDto.setMessage("Invalid credentials");
            return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
            
        } catch (AuthenticationException e) {
            responseDto.setStatus(HttpStatus.UNAUTHORIZED.value());
            responseDto.setMessage("Authentication failed: " + e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<ResponseDto> adminLogin(LoginRequest loginRequest) {
        ResponseDto responseDto = new ResponseDto();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            if (authentication.isAuthenticated()) {
                Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());
                if (userOpt.isPresent()) {
                    User user = userOpt.get();

                    if (user.getRole().getId() != 0){
                        System.out.println(user.getRole().getId());
                        responseDto.setStatus(HttpStatus.UNAUTHORIZED.value());
                        responseDto.setMessage("Unauthorized user");
                        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
                    }

                    String token = jwtUtil.generateToken(user);

                    LoginResponse resUser = new LoginResponse();
                    resUser.setId(user.getId());
                    resUser.setAccessToken(token);
                    resUser.setUsername(user.getUsername());

                    responseDto.setStatus(HttpStatus.OK.value());
                    responseDto.setMessage("Login Successful");
                    responseDto.setData(resUser);
                    return new ResponseEntity<>(responseDto, HttpStatus.OK);
                }
            }

            responseDto.setStatus(HttpStatus.UNAUTHORIZED.value());
            responseDto.setMessage("Invalid credentials");
            return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);

        } catch (AuthenticationException e) {
            responseDto.setStatus(HttpStatus.UNAUTHORIZED.value());
            responseDto.setMessage("Authentication failed: " + e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<ResponseDto> register(RegisterRequest registerRequest) {
        ResponseDto responseDto = new ResponseDto();
        
        try {
            if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
                responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
                responseDto.setMessage("Username already exists");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }

            User user = new User();
            user.setUsername(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());
            user.setEmail(registerRequest.getEmail());
            user.setMobileNo(registerRequest.getMobileNo());
            
            if (registerRequest.getDrivingLicense() != null) {
                user.setDriverLicNo(registerRequest.getDrivingLicense().getNumber());
//                user.setDlUrl(registerRequest.getDrivingLicense().getImageUrl());
            }

            Role customerRole = roleRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Default role not found"));
            user.setRole(customerRole);

            userRepository.save(user);

            responseDto.setStatus(HttpStatus.CREATED.value());
            responseDto.setMessage("Registration Successful");
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
            
        } catch (Exception e) {
            responseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setMessage("Registration failed: " + e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
