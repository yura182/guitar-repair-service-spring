package com.yura.repairservice.service.impl;

import com.yura.repairservice.domain.order.Order;
import com.yura.repairservice.domain.order.Status;
import com.yura.repairservice.domain.user.User;
import com.yura.repairservice.entity.OrderEntity;
import com.yura.repairservice.repository.OrderRepository;
import com.yura.repairservice.service.OrderService;
import com.yura.repairservice.service.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public void add(Order order) {
        orderRepository.save(orderMapper.mapOrderToOrderEntity(order));
    }

    @Override
    public Order findById(Integer id) {
        return orderRepository
                .findById(id)
                .map(orderMapper::mapOrderEntityToOrder)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Override
    public List<Order> findByMaster(Integer masterId, Pageable pageable) {
        return orderRepository
                .findAllByMasterId(masterId, pageable)
                .stream()
                .map(orderMapper::mapOrderEntityToOrder)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByClient(Integer clientId, Pageable pageable) {
        return orderRepository
                .findAllByClientId(clientId, pageable)
                .stream()
                .map(orderMapper::mapOrderEntityToOrder)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByStatus(Status status, Pageable pageable) {
        return orderRepository
                .findAllByStatus(status, pageable)
                .stream()
                .map(orderMapper::mapOrderEntityToOrder)
                .collect(Collectors.toList());
    }

    @Override
    public long numberOfEntriesByMasterId(Integer masterId) {
        return orderRepository.countByMasterId(masterId);
    }

    @Override
    public long numberOfEntriesByClientId(Integer clientId) {
        return orderRepository.countByClientId(clientId);
    }

    @Override
    public long numberOfEntriesByStatus(Status status) {
        return orderRepository.countByStatus(status);
    }

    @Override
    public void acceptOrder(Order order) {
        orderRepository.updateOrderStatus(order.getId(), Status.ACCEPTED);
    }

    @Override
    public void rejectOrder(Order order, String rejectionReason) {
        orderRepository.updateOrderStatus(order.getId(), Status.REJECTED, rejectionReason);
    }

    @Override
    public void processOrder(Order order, User master) {
        OrderEntity orderEntity = orderMapper.mapOrderToOrderEntity(order, master);
        orderRepository.updateOrderMaster(orderEntity.getId(), orderEntity.getMaster(), Status.PROCESSING);
    }

    @Override
    public void completeOrder(Order order) {
        orderRepository.updateOrderStatus(order.getId(), Status.COMPLETED);
    }

    @Override
    public void setPrice(Order order, Double price) {
        orderRepository.updateOrderPrice(order.getId(), price);
    }
}
