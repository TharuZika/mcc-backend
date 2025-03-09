package com.mcc_backend.service.impl;

//import com.taxi.dto.LoginRequest;
//import com.taxi.dto.RegisterRequest;
//import com.taxi.entity.Role;
//import com.taxi.entity.Status;
//import com.taxi.entity.User;
//import com.taxi.repository.RoleRepository;
//import com.taxi.repository.StatusRepository;
//import com.taxi.repository.UserRepository;
//import com.taxi.config.JwtUtil;
import com.mcc_backend.config.JwtUtil;
import com.mcc_backend.dto.LoginRequest;
import com.mcc_backend.dto.RegisterRequest;
import com.mcc_backend.entity.Role;
import com.mcc_backend.entity.Status;
import com.mcc_backend.entity.User;
import com.mcc_backend.repository.RoleRepository;
import com.mcc_backend.repository.StatusRepository;
import com.mcc_backend.repository.UserRepository;
import com.mcc_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            StatusRepository statusRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.statusRepository = statusRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setAuthenticationManager(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        return jwtUtil.generateToken(loginRequest.getUsername());
    }

    @Override
    public User register(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setMobileNo(registerRequest.getMobileNo());

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        Status status = statusRepository.findByName("ACTIVE")
                .orElseThrow(() -> new RuntimeException("Status not found"));
        user.setStatus(status);

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().getName())
                .build();
    }
}
