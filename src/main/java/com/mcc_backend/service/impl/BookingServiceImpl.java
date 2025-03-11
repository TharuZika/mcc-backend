package com.mcc_backend.service.impl;

import com.mcc_backend.dto.BookingRequestDto;
import com.mcc_backend.dto.BookingResponseDto;
import com.mcc_backend.dto.GuestCredentialsDto;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        System.out.println(bookingRequest.getBookingDetails());
        System.out.println(bookingRequest.getCardToken());
        System.out.println(bookingRequest.getCustomerDetails());
        System.out.println(bookingRequest.getTotalAmount());
        System.out.println(bookingRequest.getVehicle());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> existingUser = userRepository.findById(user.getId());

        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setTotalAmount(bookingRequest.getTotalAmount());
        order.setPaidAmount(BigDecimal.ZERO);
        order.setPaymentMethod(PaymentMethod.valueOf(bookingRequest.getPaymentMethod()));
        order = orderRepository.save(order);

        Booking booking = new Booking();
        booking.setOrder(order);
        booking.setUser(existingUser.get());
        booking.setBookingType(BookingType.valueOf(bookingRequest.getBookingDetails().getServiceType().toUpperCase()));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTimeStr = bookingRequest.getBookingDetails().getPickupDate() + " " + 
                           bookingRequest.getBookingDetails().getPickupTime();
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, dateFormatter);
        booking.setTripDate(localDateTime);
        
        booking.setStatus(BookingStatus.PENDING);
        booking.setVehicle(bookingRequest.getVehicle());
        booking.setPickupLocation(bookingRequest.getBookingDetails().getPickupLocation());
        booking.setDropLocation(bookingRequest.getBookingDetails().getDropLocation());
        
        booking = bookingRepository.save(booking);

        if (booking.getId() != null) {
            return new BookingResponseDto(booking, "Booking created successfully");
        }else {
            return new BookingResponseDto(booking, "Booking not created");
        }
    }
} 