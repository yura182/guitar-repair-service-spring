package com.yura.repair.service.impl;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.Status;
import com.yura.repair.repository.OrderRepository;
import com.yura.repair.service.OrderService;
import com.yura.repair.service.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public void add(OrderDto orderDto) {
        orderRepository.save(orderMapper.mapDtoToEntity(orderDto));
    }

    @Override
    public OrderDto findById(Integer id) {
        return orderRepository
                .findById(id)
                .map(orderMapper::mapEntityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Override
    public Page<OrderDto> findByMaster(Integer masterId, Pageable pageable) {
        return orderRepository
                .findAllByMasterId(masterId, pageable)
                .map(orderMapper::mapEntityToDto);
    }

    @Override
    public Page<OrderDto> findByClient(Integer clientId, Pageable pageable) {
        return orderRepository
                .findAllByClientId(clientId, pageable)
                .map(orderMapper::mapEntityToDto);
    }

    @Override
    public Page<OrderDto> findByStatus(Status status, Pageable pageable) {
        return orderRepository
                .findAllByStatus(status, pageable)
                .map(orderMapper::mapEntityToDto);
    }

    @Override
    public Page<OrderDto> findAll(Pageable pageable) {
        return orderRepository
                .findAll(pageable)
                .map(orderMapper::mapEntityToDto);
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
//        OrderEntity orderEntity = orderMapper.mapDtoToEntity(orderDto, master); TODO
//        orderRepository.updateOrderMaster(orderEntity.getId(), orderEntity.getMaster(), Status.PROCESSING);
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
