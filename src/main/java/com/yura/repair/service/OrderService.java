package com.yura.repair.service;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    void add(OrderDto orderDto);

    OrderDto findById(Integer id);

    Page<OrderDto> findByMaster(Integer masterId, Pageable pageable);

    Page<OrderDto> findByClient(Integer clientId, Pageable pageable);

    Page<OrderDto> findByStatus(Status status, Pageable pageable);

    Page<OrderDto> findAll(Pageable pageable);

    void acceptOrder(Integer orderId, Double price);

    void rejectOrder(Integer orderId, String rejectionReason);

    void processOrder(Integer orderId, UserDto master);

    void completeOrder(Integer orderId);
}
