package com.mcc_backend.repository;

import com.mcc_backend.dto.FinanceDto;
import com.mcc_backend.dto.GraphData;
import com.mcc_backend.dto.OrderHistory;
import com.mcc_backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT SUM(o.paidAmount) FROM Order o")
    BigDecimal calculateTotalRevenue();

    @Query("SELECT COUNT(o) FROM Order o")
    Long calculateTotalBookings();

    @Query("SELECT new com.mcc_backend.dto.GraphData(o.date, o.paidAmount) FROM Order o")
    List<GraphData> findGraphData();

    @Query("SELECT new com.mcc_backend.dto.OrderHistory(o.id, o.paidAmount, o.date) FROM Order o")
    List<OrderHistory> findOrderHistory();
} 