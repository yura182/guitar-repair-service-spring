package com.yura.repair.service.impl;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.OrderEntity;
import com.yura.repair.entity.Status;
import com.yura.repair.repository.OrderRepository;
import com.yura.repair.service.OrderService;
import com.yura.repair.service.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void acceptOrder(Integer orderId, Double price) {
        OrderDto orderDto = findById(orderId);

        orderDto.setPrice(price);
        orderDto.setStatus(Status.ACCEPTED);

        orderRepository.save(orderMapper.mapDtoToEntity(orderDto));
    }

    @Override
    @Transactional
    public void rejectOrder(Integer orderId, String rejectionReason) {
        OrderDto orderDto = findById(orderId);

        orderDto.setStatus(Status.REJECTED);
        orderDto.setRejectionReason(rejectionReason);

        orderRepository.save(orderMapper.mapDtoToEntity(orderDto));
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
