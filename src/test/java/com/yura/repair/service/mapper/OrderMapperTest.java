package com.yura.repair.service.mapper;

import com.yura.repair.dto.InstrumentDto;
import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.InstrumentEntity;
import com.yura.repair.entity.OrderEntity;
import com.yura.repair.entity.Status;
import com.yura.repair.entity.UserEntity;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderMapperTest {
    private static final UserDto USER_DTO = UserDto.builder().build();
    private static final UserEntity USER_ENTITY = UserEntity.builder().build();
    private static final InstrumentDto INSTRUMENT_DTO_DTO = InstrumentDto.builder().build();
    private static final InstrumentEntity INSTRUMENT_ENTITY = InstrumentEntity.builder().build();
    private static final OrderDto ORDER_DTO_DTO = getOrderDtoDto();
    private static final OrderEntity ORDER_ENTITY = getOrderEntity();

    private UserMapper userMapper;
    private InstrumentMapper instrumentMapper;
    private OrderMapper orderMapper;

    @Before
    public void init() {
        userMapper = mock(UserMapper.class);
        instrumentMapper = mock(InstrumentMapper.class);

        orderMapper = new OrderMapper(userMapper, instrumentMapper);
    }

    @Test
    public void mapEntityToDtoShouldMapToDto() {
        when(userMapper.mapEntityToDto(USER_ENTITY)).thenReturn(USER_DTO);
        when(instrumentMapper.mapEntityToDto(INSTRUMENT_ENTITY)).thenReturn(INSTRUMENT_DTO_DTO);

        OrderDto actual = orderMapper.mapEntityToDto(ORDER_ENTITY);

        assertEquals(ORDER_DTO_DTO, actual);
    }

    @Test
    public void mapDtoToEntityShouldReturnEntity() {
        when(userMapper.mapDtoToEntity(USER_DTO)).thenReturn(USER_ENTITY);
        when(instrumentMapper.mapDtoToEntity(INSTRUMENT_DTO_DTO)).thenReturn(INSTRUMENT_ENTITY);

        OrderEntity actual = orderMapper.mapDtoToEntity(ORDER_DTO_DTO);

        assertEquals(ORDER_ENTITY, actual);
    }

    @Test
    public void mapEntityToDtoShouldReturnNull() {
        OrderDto actual = orderMapper.mapEntityToDto(null);

        assertNull(actual);
    }

    @Test
    public void mapDtoToEntityShouldReturnNull() {
        OrderEntity actual = orderMapper.mapDtoToEntity(null);

        assertNull(actual);
    }

    private static OrderDto getOrderDtoDto() {
        return OrderDto.builder()
                .id(1)
                .client(USER_DTO)
                .master(USER_DTO)
                .instrumentDto(INSTRUMENT_DTO_DTO)
                .dateTime(LocalDateTime.of(1990, 12, 12, 12, 12))
                .service("Service")
                .price(1.1)
                .status(Status.NEW)
                .build();
    }

    private static OrderEntity getOrderEntity() {
        return OrderEntity.builder()
                .id(1)
                .client(USER_ENTITY)
                .master(USER_ENTITY)
                .instrument(INSTRUMENT_ENTITY)
                .dateTime(LocalDateTime.of(1990, 12, 12, 12, 12))
                .service("Service")
                .price(1.1)
                .status(Status.NEW)
                .build();
    }
}