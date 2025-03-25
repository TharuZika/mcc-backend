//package com.mcc_backend.service;
//
//import com.mcc_backend.config.JwtUtil;
//import com.mcc_backend.dto.LoginRequest;
//import com.mcc_backend.dto.RegisterRequest;
//import com.mcc_backend.entity.Role;
//import com.mcc_backend.entity.Status;
//import com.mcc_backend.entity.User;
//import com.mcc_backend.repository.RoleRepository;
//import com.mcc_backend.repository.StatusRepository;
//import com.mcc_backend.repository.UserRepository;
//import com.mcc_backend.service.impl.AuthServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class AuthServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private RoleRepository roleRepository;
//
//    @Mock
//    private StatusRepository statusRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private JwtUtil jwtUtil;
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @InjectMocks
//    private AuthServiceImpl authService;
//
//    private LoginRequest loginRequest;
//    private RegisterRequest registerRequest;
//    private User mockUser;
//    private Role mockRole;
//    private Status mockStatus;
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
//        mockRole = new Role();
//        mockRole.setId(1L);
//        mockRole.setName("USER");
//
//        mockStatus = new Status();
//        mockStatus.setId(1L);
//        mockStatus.setName("ACTIVE");
//
//        mockUser = new User();
//        mockUser.setId(1L);
//        mockUser.setUsername("newuser");
//        mockUser.setEmail("newuser@test.com");
//        mockUser.setRole(mockRole);
//        mockUser.setStatus(mockStatus);
//    }
//
//    @Test
//    void loginSuccess() {
//        when(userRepository.findByUsername(loginRequest.getUsername()))
//                .thenReturn(Optional.of(mockUser));
//        when(jwtUtil.generateToken(anyString(), anyString()))
//                .thenReturn("mock.jwt.token");
//
//        String token = authService.login(loginRequest);
//
//        assertNotNull(token);
//        assertEquals("mock.jwt.token", token);
//        verify(authenticationManager).authenticate(
//                any(UsernamePasswordAuthenticationToken.class)
//        );
//    }
//
//    @Test
//    void loginFailUserNotFound() {
//        when(userRepository.findByUsername(loginRequest.getUsername()))
//                .thenReturn(Optional.empty());
//
//        assertThrows(UsernameNotFoundException.class, () -> {
//            authService.login(loginRequest);
//        });
//    }
//
//    @Test
//    void registerSuccess() {
//        when(roleRepository.findByName("USER")).thenReturn(Optional.of(mockRole));
//        when(statusRepository.findByName("ACTIVE")).thenReturn(Optional.of(mockStatus));
//        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
//        when(userRepository.save(any(User.class))).thenReturn(mockUser);
//
//        User registeredUser = authService.register(registerRequest);
//
//        assertNotNull(registeredUser);
//        assertEquals(mockUser.getUsername(), registeredUser.getUsername());
//        assertEquals(mockUser.getEmail(), registeredUser.getEmail());
//        verify(userRepository).save(any(User.class));
//    }
//
//    @Test
//    void registerFailRoleNotFound() {
//        when(roleRepository.findByName("USER")).thenReturn(Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> {
//            authService.register(registerRequest);
//        });
//    }
//}