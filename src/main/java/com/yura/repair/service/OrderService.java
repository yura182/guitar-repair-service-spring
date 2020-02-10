package com.yura.repair.service;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.entity.Status;
import com.yura.repair.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    void add(OrderDto orderDto);

    OrderDto findById(Integer id);

    Page<OrderDto> findByMaster(Integer masterId, Pageable pageable);

    Page<OrderDto> findByClient(Integer clientId, Pageable pageable);

    Page<OrderDto> findByStatus(Status status, Pageable pageable);

    Page<OrderDto> findAll(Pageable pageable);

    void acceptOrder(OrderDto orderDto);

    void rejectOrder(OrderDto orderDto, String rejectionReason);

    void processOrder(OrderDto orderDto, UserDto master);

    void completeOrder(OrderDto orderDto);

    void setPrice(OrderDto orderDto, Double price);
}
