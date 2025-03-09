package com.mcc_backend.service.impl;

import com.mcc_backend.dto.BookingRequestDto;
import com.mcc_backend.dto.BookingResponseDto;
import com.mcc_backend.dto.RegisterRequest;
import com.mcc_backend.entity.*;
import com.mcc_backend.entity.enums.BookingStatus;
import com.mcc_backend.entity.enums.BookingType;
import com.mcc_backend.entity.enums.PaymentMethod;
import com.mcc_backend.repository.BookingRepository;
import com.mcc_backend.repository.OrderRepository;
import com.mcc_backend.repository.UserRepository;
import com.mcc_backend.service.AuthService;
import com.mcc_backend.service.BookingService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public BookingServiceImpl(
            BookingRepository bookingRepository,
            OrderRepository orderRepository,
            UserRepository userRepository,
            AuthService authService,
            PasswordEncoder passwordEncoder) {
        this.bookingRepository = bookingRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto bookingRequest) {
        // Find or create user
        Optional<User> existingUser = userRepository.findByEmail(bookingRequest.getCustomerDetails().getEmail());
        User user;
        GuestCredentialsDto guestCredentials = null;

        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            // Create guest user
            String tempPassword = UUID.randomUUID().toString().substring(0, 8);
            String username = bookingRequest.getCustomerDetails().getEmail().split("@")[0] + 
                            UUID.randomUUID().toString().substring(0, 4);

            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setUsername(username);
            registerRequest.setPassword(tempPassword);
            registerRequest.setEmail(bookingRequest.getCustomerDetails().getEmail());
            registerRequest.setMobileNo(bookingRequest.getCustomerDetails().getPhone());
            
            String[] names = bookingRequest.getCustomerDetails().getName().split(" ", 2);
            registerRequest.setFirstName(names[0]);
            registerRequest.setLastName(names.length > 1 ? names[1] : "");

            user = authService.register(registerRequest);
            guestCredentials = new GuestCredentialsDto(username, tempPassword);
        }

        // Create order
        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setTotalAmount(bookingRequest.getTotalAmount());
        order.setPaidAmount(BigDecimal.ZERO);
        order.setPaymentMethod(PaymentMethod.valueOf(bookingRequest.getPaymentMethod()));
        order = orderRepository.save(order);

        // Create booking
        Booking booking = new Booking();
        booking.setOrder(order);
        booking.setUser(user);
        booking.setBookingType(BookingType.valueOf(bookingRequest.getBookingDetails().getServiceType().toUpperCase()));
        
        // Parse date and time
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTimeStr = bookingRequest.getBookingDetails().getPickupDate() + " " + 
                           bookingRequest.getBookingDetails().getPickupTime();
        booking.setTripDate(LocalDateTime.parse(dateTimeStr, dateFormatter));
        
        booking.setStatus(BookingStatus.PENDING);
        booking.setVehicle(bookingRequest.getSelectedVehicle());
        booking.setPickupLocation(bookingRequest.getBookingDetails().getPickupLocation());
        booking.setDropLocation(bookingRequest.getBookingDetails().getDropLocation());
        
        booking = bookingRepository.save(booking);

        // Return response with or without guest credentials
        if (guestCredentials != null) {
            return new BookingResponseDto(booking, guestCredentials, "Booking created successfully");
        } else {
            return new BookingResponseDto(booking, "Booking created successfully");
        }
    }
} 