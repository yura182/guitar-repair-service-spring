package com.yura.repair.service;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.entity.Status;
import com.yura.repair.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    void add(OrderDto orderDto);

    OrderDto findById(Integer id);

    List<OrderDto> findByMaster(Integer masterId, Pageable pageable);

    List<OrderDto> findByClient(Integer clientId, Pageable pageable);

    List<OrderDto> findByStatus(Status status, Pageable pageable);

    List<OrderDto> findAll(Pageable pageable);

    long numberOfEntriesByMasterId(Integer masterId);

    long numberOfEntriesByClientId(Integer clientID);

    long numberOfEntriesByStatus(Status status);

    long numberOfEntries();

    void acceptOrder(OrderDto orderDto);

    void rejectOrder(OrderDto orderDto, String rejectionReason);

    void processOrder(OrderDto orderDto, UserDto master);

    void completeOrder(OrderDto orderDto);

    void setPrice(OrderDto orderDto, Double price);
}
