package com.mcc_backend.service;

import com.mcc_backend.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

public interface UserService {
    ResponseEntity<Object> registerUser(UserDto user) throws Exception;

    ResponseEntity<Object> createUser(UserDto user) throws Exception, IOException;

    ResponseEntity<Object> updateUser(UserDto user) throws Exception, IOException;

    ResponseEntity<Object> changeUserStatus(Integer userId, Character status) throws Exception;

    ResponseEntity<Object> getUserDetails(Integer userId);

    ResponseEntity<Object> confirmUserEmail(String uuid) throws Exception;

    ResponseEntity<Object> getUserAddress();

    ResponseEntity<Object> resetPassword(Map<String, Object> requestBody) throws Exception;

    ResponseEntity<Object> getLoggedInUserDetails();

    ResponseEntity<Object> getAllUsers();

    ResponseEntity<Object> getAllDrivers();

    ResponseEntity<Object> getAllAdmins();
}
