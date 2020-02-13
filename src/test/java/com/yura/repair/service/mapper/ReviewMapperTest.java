package com.yura.repair.service.mapper;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.ReviewDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.OrderEntity;
import com.yura.repair.entity.ReviewEntity;
import com.yura.repair.entity.UserEntity;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReviewMapperTest {
    private static final UserDto USER_DTO = UserDto.builder().build();
    private static final UserEntity USER_ENTITY = UserEntity.builder().build();
    private static final OrderDto ORDER_DTO_DTO = OrderDto.builder().build();
    private static final OrderEntity ORDER_ENTITY = OrderEntity.builder().build();
    private static final ReviewDto REVIEW_DTO = getReviewDto();
    private static final ReviewEntity REVIEW_ENTITY = getReviewEntity();

    private UserMapper userMapper;
    private OrderMapper orderMapper;
    private ReviewMapper reviewMapper;

    @Before
    public void init() {
        userMapper = mock(UserMapper.class);
        orderMapper = mock(OrderMapper.class);

        reviewMapper = new ReviewMapper(userMapper, orderMapper);
    }

    @Test
    public void mapDtoToEntityShouldMapToEntity() {
        when(userMapper.mapDtoToEntity(USER_DTO)).thenReturn(USER_ENTITY);
        when(orderMapper.mapDtoToEntity(ORDER_DTO_DTO)).thenReturn(ORDER_ENTITY);

        ReviewEntity actual = reviewMapper.mapDtoToEntity(REVIEW_DTO);

        assertEquals(REVIEW_ENTITY, actual);
    }

    @Test
    public void mapEntityToDtoShouldMapToDto() {
        when(userMapper.mapEntityToDto(USER_ENTITY)).thenReturn(USER_DTO);
        when(orderMapper.mapEntityToDto(ORDER_ENTITY)).thenReturn(ORDER_DTO_DTO);

        ReviewDto actual = reviewMapper.mapEntityToDto(REVIEW_ENTITY);

        assertEquals(REVIEW_DTO, actual);
    }

    @Test
    public void mapDtoToEntityShouldReturnNull() {
        ReviewEntity actual = reviewMapper.mapDtoToEntity(null);

        assertNull(actual);
    }

    @Test
    public void mapEntityToDtoShouldReturnNull() {
        ReviewDto actual = reviewMapper.mapEntityToDto(null);

        assertNull(actual);
    }

    private static ReviewDto getReviewDto() {
        return ReviewDto.builder()
                .id(1)
                .orderDto(ORDER_DTO_DTO)
                .client(USER_DTO)
                .text("Text")
                .date(LocalDateTime.of(1990, 12, 12, 12, 12))
                .build();
    }

    private static ReviewEntity getReviewEntity() {
        return ReviewEntity.builder()
                .id(1)
                .order(ORDER_ENTITY)
                .client(USER_ENTITY)
                .text("Text")
                .date(LocalDateTime.of(1990, 12, 12, 12, 12))
                .build();
    }
}