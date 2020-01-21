package com.yura.repairservice.service;

import com.yura.repairservice.domain.order.Order;
import com.yura.repairservice.domain.order.Status;
import com.yura.repairservice.domain.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    void add(Order order);

    Order findById(Integer id);

    List<Order> findByMaster(Integer masterId, Pageable pageable);

    List<Order> findByClient(Integer clientId, Pageable pageable);

    List<Order> findByStatus(Status status, Pageable pageable);

    List<Order> findAll(Pageable pageable);

    long numberOfEntriesByMasterId(Integer masterId);

    long numberOfEntriesByClientId(Integer clientID);

    long numberOfEntriesByStatus(Status status);

    long numberOfEntries();

    void acceptOrder(Order order);

    void rejectOrder(Order order, String rejectionReason);

    void processOrder(Order order, User master);

    void completeOrder(Order order);

    void setPrice(Order order, Double price);
}
