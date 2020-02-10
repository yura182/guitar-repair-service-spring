package com.yura.repair.service.impl;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.entity.Status;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.OrderEntity;
import com.yura.repair.repository.OrderRepository;
import com.yura.repair.service.OrderService;
import com.yura.repair.service.mapper.OrderMapper;
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
    public void add(OrderDto orderDto) {
        orderRepository.save(orderMapper.mapOrderToOrderEntity(orderDto));
    }

    @Override
    public OrderDto findById(Integer id) {
        return orderRepository
                .findById(id)
                .map(orderMapper::mapOrderEntityToOrder)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> findByMaster(Integer masterId, Pageable pageable) {
        return orderRepository
                .findAllByMasterId(masterId, pageable)
                .stream()
                .map(orderMapper::mapOrderEntityToOrder)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findByClient(Integer clientId, Pageable pageable) {
        return orderRepository
                .findAllByClientId(clientId, pageable)
                .stream()
                .map(orderMapper::mapOrderEntityToOrder)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findByStatus(Status status, Pageable pageable) {
        return orderRepository
                .findAllByStatus(status, pageable)
                .stream()
                .map(orderMapper::mapOrderEntityToOrder)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findAll(Pageable pageable) {
        return orderRepository
                .findAll(pageable)
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
    public long numberOfEntries() {
        return orderRepository.count();
    }

    @Override
    public void acceptOrder(OrderDto orderDto) {
        orderRepository.updateOrderStatus(orderDto.getId(), Status.ACCEPTED);
    }

    @Override
    public void rejectOrder(OrderDto orderDto, String rejectionReason) {
        orderRepository.updateOrderStatus(orderDto.getId(), Status.REJECTED, rejectionReason);
    }

    @Override
    public void processOrder(OrderDto orderDto, UserDto master) {
        OrderEntity orderEntity = orderMapper.mapOrderToOrderEntity(orderDto, master);
        orderRepository.updateOrderMaster(orderEntity.getId(), orderEntity.getMaster(), Status.PROCESSING);
    }

    @Override
    public void completeOrder(OrderDto orderDto) {
        orderRepository.updateOrderStatus(orderDto.getId(), Status.COMPLETED);
    }

    @Override
    public void setPrice(OrderDto orderDto, Double price) {
        orderRepository.updateOrderPrice(orderDto.getId(), price);
    }
}
