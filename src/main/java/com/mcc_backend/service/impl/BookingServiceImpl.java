package com.mcc_backend.service.impl;

import com.mcc_backend.dto.*;
import com.mcc_backend.entity.*;
import com.mcc_backend.entity.enums.BookingStatus;
import com.mcc_backend.entity.enums.BookingType;
import com.mcc_backend.entity.enums.PaymentMethod;
import com.mcc_backend.repository.BookingRepository;
import com.mcc_backend.repository.OrderRepository;
import com.mcc_backend.repository.UserRepository;
import com.mcc_backend.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto bookingRequest) {
        try {
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
            order.setPaidAmount(bookingRequest.getTotalAmount());
            order.setPaymentMethod(PaymentMethod.valueOf(bookingRequest.getPaymentMethod()));
            order = orderRepository.save(order);

            Booking booking = new Booking();
            booking.setOrder(order);
            booking.setUser(existingUser.get());
            booking.setBookingType(BookingType.valueOf(bookingRequest.getBookingDetails().getServiceType().toUpperCase()));

            LocalDate localDate = bookingRequest.getBookingDetails().getPickupDate();
            LocalDateTime localDateTime = localDate.atStartOfDay(); // Convert to LocalDateTime
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
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new BookingResponseDto(null, "Booking not created");
        }
    }

    @Override
    public BookingsListResponse fetchBookings() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Booking> byUserWithin = bookingRepository.findByUser(user);

        BookingsListResponse response = new BookingsListResponse(null, 0, 0, 1);
        response.setTotalCount(byUserWithin.size());
        response.setBookings(byUserWithin);

        return response;
    }
}