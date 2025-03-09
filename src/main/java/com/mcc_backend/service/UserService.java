package com.mcc_backend.service;

import com.mcc_backend.dto.UserDto;
import com.mcc_backend.util.CustomCheckedException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface UserService {

    ResponseEntity<Object> registerUser(UserDto user) throws CustomCheckedException;

    ResponseEntity<Object> createUser(UserDto user) throws CustomCheckedException, IOException;

    ResponseEntity<Object> updateUser(UserDto user) throws CustomCheckedException, IOException;

    ResponseEntity<Object> changeUserStatus(Integer userId, Character status) throws CustomCheckedException;

    ResponseEntity<Object> getUserDetails(Integer userId);
}

