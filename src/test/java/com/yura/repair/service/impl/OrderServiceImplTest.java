package com.yura.repair.service.impl;

import com.yura.repair.dto.InstrumentDto;
import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.InstrumentEntity;
import com.yura.repair.entity.OrderEntity;
import com.yura.repair.entity.Status;
import com.yura.repair.entity.UserEntity;
import com.yura.repair.exception.OrderAlreadyUpdatedException;
import com.yura.repair.repository.OrderRepository;
import com.yura.repair.service.mapper.EntityMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = OrderServiceImpl.class)
public class OrderServiceImplTest {
    private static final OrderDto ORDER_DTO = getOrderDto();
    private static final OrderEntity ORDER_ENTITY = getOrderEntity();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private EntityMapper<OrderEntity, OrderDto> orderMapper;

    @MockBean
    private EntityMapper<UserEntity, UserDto> userMapper;

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void addShouldAddOrder() {
        when(orderMapper.mapDtoToEntity(ORDER_DTO)).thenReturn(ORDER_ENTITY);

        orderService.add(ORDER_DTO);

        verify(orderRepository).save(ORDER_ENTITY);
    }

    @Test
    public void findByIdShouldReturnOrder() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(ORDER_ENTITY));
        when(orderMapper.mapEntityToDto(ORDER_ENTITY)).thenReturn(ORDER_DTO);

        OrderDto actual = orderService.findById(1);

        assertEquals(ORDER_DTO, actual);
    }

    @Test
    public void findByIdShouldThrowUOrderNotFoundException() {
        exception.expect(EntityNotFoundException.class);
        exception.expectMessage("Order not found");

        when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());

        orderService.findById(1);
    }

    @Test
    public void findAllShouldReturnPageOfOrders() {
        Page<OrderDto> expected = new PageImpl<>(Collections.singletonList(ORDER_DTO));
        Page<OrderEntity> userEntities = new PageImpl<>(Collections.singletonList(ORDER_ENTITY));
        Pageable pageable = PageRequest.of(1, 1);

        when(orderRepository.findAll(pageable)).thenReturn(userEntities);
        when(orderMapper.mapEntityToDto(ORDER_ENTITY)).thenReturn(ORDER_DTO);

        Page<OrderDto> actual = orderService.findAll(pageable);

        assertEquals(expected, actual);
    }

    @Test
    public void findAllShouldReturnEmptyPage() {
        Page<OrderDto> expected = Page.empty();
        ;
        Page<OrderEntity> userEntities = Page.empty();
        Pageable pageable = PageRequest.of(1, 1);

        when(orderRepository.findAll(pageable)).thenReturn(userEntities);
        when(orderMapper.mapEntityToDto(ORDER_ENTITY)).thenReturn(ORDER_DTO);

        Page<OrderDto> actual = orderService.findAll(pageable);

        assertEquals(expected, actual);
    }

    @Test
    public void findByClientShouldReturnPageOfOrders() {
        Page<OrderDto> expected = new PageImpl<>(Collections.singletonList(ORDER_DTO));
        Page<OrderEntity> userEntities = new PageImpl<>(Collections.singletonList(ORDER_ENTITY));
        Pageable pageable = PageRequest.of(1, 1);

        when(orderRepository.findAllByClientId(1, pageable)).thenReturn(userEntities);
        when(orderMapper.mapEntityToDto(ORDER_ENTITY)).thenReturn(ORDER_DTO);

        Page<OrderDto> actual = orderService.findByClient(1, pageable);

        assertEquals(expected, actual);
    }

    @Test
    public void findByClientShouldReturnEmptyPage() {
        Page<OrderDto> expected = Page.empty();
        Page<OrderEntity> userEntities = Page.empty();
        Pageable pageable = PageRequest.of(1, 1);

        when(orderRepository.findAllByClientId(2, pageable)).thenReturn(userEntities);
        when(orderMapper.mapEntityToDto(ORDER_ENTITY)).thenReturn(ORDER_DTO);

        Page<OrderDto> actual = orderService.findByClient(2, pageable);

        assertEquals(expected, actual);
    }

    @Test
    public void findByMasterShouldReturnPageOfOrders() {
        Page<OrderDto> expected = new PageImpl<>(Collections.singletonList(ORDER_DTO));
        Page<OrderEntity> userEntities = new PageImpl<>(Collections.singletonList(ORDER_ENTITY));
        Pageable pageable = PageRequest.of(1, 1);

        when(orderRepository.findAllByMasterId(1, pageable)).thenReturn(userEntities);
        when(orderMapper.mapEntityToDto(ORDER_ENTITY)).thenReturn(ORDER_DTO);

        Page<OrderDto> actual = orderService.findByMaster(1, pageable);

        assertEquals(expected, actual);
    }

    @Test
    public void findByMasterShouldReturnEmptyPage() {
        Page<OrderDto> expected = Page.empty();
        Page<OrderEntity> userEntities = Page.empty();
        Pageable pageable = PageRequest.of(1, 1);

        when(orderRepository.findAllByMasterId(1, pageable)).thenReturn(userEntities);
        when(orderMapper.mapEntityToDto(ORDER_ENTITY)).thenReturn(ORDER_DTO);

        Page<OrderDto> actual = orderService.findByMaster(1, pageable);

        assertEquals(expected, actual);
    }

    @Test
    public void findByStatusShouldReturnPageOfOrders() {
        Page<OrderDto> expected = new PageImpl<>(Collections.singletonList(ORDER_DTO));
        Page<OrderEntity> userEntities = new PageImpl<>(Collections.singletonList(ORDER_ENTITY));
        Pageable pageable = PageRequest.of(1, 1);

        when(orderRepository.findAllByStatus(Status.ACCEPTED, pageable)).thenReturn(userEntities);
        when(orderMapper.mapEntityToDto(ORDER_ENTITY)).thenReturn(ORDER_DTO);

        Page<OrderDto> actual = orderService.findByStatus(Status.ACCEPTED, pageable);

        assertEquals(expected, actual);
        ;
    }

    @Test
    public void findByStatusShouldReturnEmptyPage() {
        Page<OrderDto> expected = Page.empty();
        Page<OrderEntity> userEntities = Page.empty();
        Pageable pageable = PageRequest.of(1, 1);

        when(orderRepository.findAllByStatus(Status.ACCEPTED, pageable)).thenReturn(userEntities);
        when(orderMapper.mapEntityToDto(ORDER_ENTITY)).thenReturn(ORDER_DTO);

        Page<OrderDto> actual = orderService.findByStatus(Status.ACCEPTED, pageable);

        assertEquals(expected, actual);
        ;
    }

    @Test
    public void acceptOrderShouldUpdateOrder() {
        OrderEntity orderEntity = OrderEntity.builder().status(Status.NEW).build();
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orderEntity));

        orderService.acceptOrder(1, 1.1);

        verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
    public void rejectOrderShouldUpdateOrder() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(ORDER_ENTITY));

        orderService.rejectOrder(1, "reason");

        verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
    public void processOrderShouldUpdateOrder() {
        OrderEntity orderEntity = getOrderEntity();
        orderEntity.setStatus(Status.ACCEPTED);

        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orderEntity));

        orderService.processOrder(1, UserDto.builder().build());

        verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
    public void processOrderShouldThrowOrderAlreadyUpdatedException() {
        exception.expect(OrderAlreadyUpdatedException.class);
        exception.expectMessage("Order already processed by another master");

        OrderEntity orderEntity = getOrderEntity();
        orderEntity.setStatus(Status.PROCESSING);

        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orderEntity));

        orderService.processOrder(1, UserDto.builder().build());
    }

    @Test
    public void completeOrderShouldUpdateOrder() {
        OrderEntity orderEntity = OrderEntity.builder().status(Status.PROCESSING).build();
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orderEntity));

        orderService.completeOrder(1);

        verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
    public void isNotUserOrderShouldReturnFalse() {
        boolean actual = orderService.isNotUserOrder(UserDto.builder().id(1).build(), ORDER_DTO);
        assertFalse(actual);
    }

    @Test
    public void isNotUserOrderShouldReturnTrue() {
        boolean actual = orderService.isNotUserOrder(UserDto.builder().id(3).build(), ORDER_DTO);
        assertTrue(actual);
    }

    @Test
    public void isNotMasterOrderShouldReturnFalse() {
        boolean actual = orderService.isNotMasterOrder(UserDto.builder().id(2).build(), ORDER_DTO);
        assertFalse(actual);
    }

    @Test
    public void isNotMasterOrderShouldReturnTrue() {
        boolean actual = orderService.isNotMasterOrder(UserDto.builder().id(7).build(), ORDER_DTO);
        assertTrue(actual);
    }

    @Test
    public void isNotMasterOrderShouldReturnFalseForNull() {
        OrderDto orderDto = getOrderDto();
        orderDto.setMaster(null);
        boolean actual = orderService.isNotMasterOrder(UserDto.builder().id(7).build(), orderDto);
        assertFalse(actual);
    }

    private static OrderDto getOrderDto() {
        return OrderDto.builder()
                .instrumentDto(InstrumentDto.builder()
                        .brand("Cort")
                        .model("123")
                        .manufactureYear("1995")
                        .build())
                .status(Status.NEW)
                .dateTime(LocalDateTime.of(1990, 12, 12, 12, 12))
                .client(UserDto.builder()
                        .id(1)
                        .name("Yura")
                        .build())
                .service("Service")
                .master(UserDto.builder()
                        .id(2)
                        .build())
                .build();
    }

    private static OrderEntity getOrderEntity() {
        return OrderEntity.builder()
                .id(1)
                .instrument(InstrumentEntity.builder()
                        .id(1)
                        .brand("Cort")
                        .model("123")
                        .manufactureYear(1995)
                        .build())
                .status(Status.NEW)
                .dateTime(LocalDateTime.of(1990, 12, 12, 12, 12))
                .client(UserEntity.builder()
                        .id(1)
                        .name("Yura")
                        .build())
                .service("Service")
                .build();
    }
}