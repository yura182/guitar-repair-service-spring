package com.yura.repair.service.impl;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.OrderEntity;
import com.yura.repair.entity.Status;
import com.yura.repair.entity.UserEntity;
import com.yura.repair.exception.OrderAlreadyUpdatedException;
import com.yura.repair.repository.OrderRepository;
import com.yura.repair.service.OrderService;
import com.yura.repair.service.mapper.EntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final EntityMapper<OrderEntity, OrderDto> orderMapper;
    private final EntityMapper<UserEntity, UserDto> userMapper;

    @Override
    public void add(OrderDto orderDto) {
        orderRepository.save(orderMapper.mapDtoToEntity(orderDto));
    }

    @Override
    public OrderDto findById(Integer id) {
        return orderMapper.mapEntityToDto(getOrderById(id));
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
        OrderEntity orderEntity = getOrderById(orderId);

        orderEntity.setPrice(price);
        orderEntity.setStatus(Status.ACCEPTED);

        orderRepository.save(orderEntity);
    }

    @Override
    @Transactional
    public void rejectOrder(Integer orderId, String rejectionReason) {
        OrderEntity orderEntity = getOrderById(orderId);

        orderEntity.setStatus(Status.REJECTED);
        orderEntity.setRejectionReason(rejectionReason);

        orderRepository.save(orderEntity);
    }

    @Override
    @Transactional
    public void processOrder(Integer orderId, UserDto master) {
        OrderEntity orderEntity = getOrderById(orderId);

        if (orderEntity.getStatus() != Status.ACCEPTED) {
            throw new OrderAlreadyUpdatedException("Order's already processed by another master");
        }

        orderEntity.setStatus(Status.PROCESSING);
        orderEntity.setMaster(userMapper.mapDtoToEntity(master));

        orderRepository.save(orderEntity);
    }

    @Override
    @Transactional
    public void completeOrder(Integer orderId) {
        OrderEntity orderEntity = getOrderById(orderId);

        orderEntity.setStatus(Status.COMPLETED);

        orderRepository.save(orderEntity);
    }

    @Override
    public boolean isNotUserOrder(UserDto userDto, OrderDto orderDto) {
        return !orderDto.getClient().getId().equals(userDto.getId());
    }

    @Override
    public boolean isNotMasterOrder(UserDto masterDto, OrderDto orderDto) {
        return Objects.nonNull(orderDto.getMaster()) && !orderDto.getMaster().getId().equals(masterDto.getId());
    }

    private OrderEntity getOrderById(Integer orderId) {
        return orderRepository
                .findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }
}
