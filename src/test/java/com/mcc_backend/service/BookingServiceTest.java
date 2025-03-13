package com.mcc_backend.service;

import com.mcc_backend.dto.BookingRequestDto;
import com.mcc_backend.dto.BookingResponseDto;
import com.mcc_backend.dto.BookingsListResponse;
import com.mcc_backend.entity.Booking;
import com.mcc_backend.entity.Order;
import com.mcc_backend.entity.User;
import com.mcc_backend.entity.Vehicle;
import com.mcc_backend.entity.enums.BookingStatus;
import com.mcc_backend.entity.enums.PaymentMethod;
import com.mcc_backend.repository.BookingRepository;
import com.mcc_backend.repository.OrderRepository;
import com.mcc_backend.repository.UserRepository;
import com.mcc_backend.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private BookingRequestDto bookingRequest;
    private User mockUser;
    private Vehicle mockVehicle;
    private Booking mockBooking;
    private Order mockOrder;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");

        mockVehicle = new Vehicle();
        mockVehicle.setId(1L);
        mockVehicle.setModel("Toyota Camry");
        mockVehicle.setDailyRate(BigDecimal.valueOf(50.00));

        mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setTotalAmount(BigDecimal.valueOf(100.00));
        mockOrder.setPaymentMethod(PaymentMethod.CARD);

        mockBooking = new Booking();
        mockBooking.setId(1L);
        mockBooking.setUser(mockUser);
        mockBooking.setVehicle(mockVehicle);
        mockBooking.setOrder(mockOrder);
        mockBooking.setStatus(BookingStatus.PENDING);
        mockBooking.setTripDate(LocalDateTime.now().plusDays(1));

        bookingRequest = new BookingRequestDto();
        // Set necessary booking request fields

        // Mock security context
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(mockUser);
    }

    @Test
    void createBookingSuccess() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);
        when(bookingRepository.save(any(Booking.class))).thenReturn(mockBooking);

        BookingResponseDto response = bookingService.createBooking(bookingRequest);

        assertNotNull(response);
        assertEquals(mockBooking.getId(), response.getBookingId());
        verify(orderRepository).save(any(Order.class));
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void fetchBookingsSuccess() {
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(mockBooking));

        BookingsListResponse response = bookingService.fetchBookings();

        assertNotNull(response);
        assertFalse(response.getBookings().isEmpty());
        assertEquals(1, response.getBookings().size());
        assertEquals(mockBooking.getId(), response.getBookings().get(0).getBookingId());
    }

    @Test
    void createBookingUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            bookingService.createBooking(bookingRequest);
        });

        verify(orderRepository, never()).save(any(Order.class));
        verify(bookingRepository, never()).save(any(Booking.class));
    }
} 