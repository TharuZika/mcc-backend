package com.mcc_backend.service.impl;

import com.mcc_backend.dto.UserDto;
import com.mcc_backend.entity.User;
import com.mcc_backend.repository.UserRepository;
import com.mcc_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Object> registerUser(UserDto user) throws Exception {
        User newUser = new User(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> createUser(UserDto user) throws Exception, IOException {
        User newUser = new User(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> updateUser(UserDto user) throws Exception, IOException {
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (!userOptional.isPresent()) {
            throw new Exception("User not found");
        }
        User existingUser = userOptional.get();
        existingUser.updateUserDetails(user);
        return new ResponseEntity<>(userRepository.save(existingUser), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> changeUserStatus(Integer userId, Character status) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Object> getUserDetails(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> confirmUserEmail(String uuid) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Object> getUserAddress() {
        return null;
    }

    @Override
    public ResponseEntity<Object> resetPassword(Map<String, Object> requestBody) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Object> getLoggedInUserDetails() {
        return null;
    }

    @Override
    public ResponseEntity<Object> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllDrivers() {
        return null;
    }

    @Override
    public ResponseEntity<Object> getAllAdmins() {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName())))
                .build();
    }
}
