//package com.mcc_backend.controller;
//
//import com.mcc_backend.dto.ResponseDto;
//import com.mcc_backend.entity.Vehicle;
//import com.mcc_backend.service.VehicleService;
//import com.mcc_backend.util.CommonConstants;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(VehicleController.class)
//class VehicleControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private VehicleService vehicleService;
//
//    private List<Vehicle> mockTaxiVehicles;
//    private List<Vehicle> mockRentalVehicles;
//
//    @BeforeEach
//    void setUp() {
//        Vehicle taxi = new Vehicle();
//        taxi.setId(1L);
//        taxi.setModel("Toyota Camry");
//        taxi.setYear(2022);
//        taxi.setIsTaxi(true);
//        taxi.setDailyRate(BigDecimal.valueOf(50.00));
//
//        Vehicle rental = new Vehicle();
//        rental.setId(2L);
//        rental.setModel("Honda Civic");
//        rental.setYear(2021);
//        rental.setIsRent(true);
//        rental.setDailyRate(BigDecimal.valueOf(45.00));
//
//        mockTaxiVehicles = Arrays.asList(taxi);
//        mockRentalVehicles = Arrays.asList(rental);
//    }
//
//    @Test
//    void getAllTaxiVehiclesSuccess() throws Exception {
//        when(vehicleService.getAllTaxiVehicles()).thenReturn(mockTaxiVehicles);
//
//        mockMvc.perform(get("/api/vehicles/taxi"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(CommonConstants.STATUS_OK))
//                .andExpect(jsonPath("$.message").value("Taxi vehicles retrieved successfully"))
//                .andExpect(jsonPath("$.data[0].model").value("Toyota Camry"))
//                .andExpect(jsonPath("$.data[0].isTaxi").value(true));
//    }
//
//    @Test
//    void getAllRentalVehiclesSuccess() throws Exception {
//        when(vehicleService.getAllRentalVehicles()).thenReturn(mockRentalVehicles);
//
//        mockMvc.perform(get("/api/vehicles/rental"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(CommonConstants.STATUS_OK))
//                .andExpect(jsonPath("$.message").value("Rental vehicles retrieved successfully"))
//                .andExpect(jsonPath("$.data[0].model").value("Honda Civic"))
//                .andExpect(jsonPath("$.data[0].isRent").value(true));
//    }
//}