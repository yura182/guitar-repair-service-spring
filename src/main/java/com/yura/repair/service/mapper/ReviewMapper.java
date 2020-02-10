package com.yura.repair.service.mapper;

import com.yura.repair.dto.ReviewDto;
import com.yura.repair.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class ReviewMapper implements EntityMapper<ReviewEntity, ReviewDto> {
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;

    @Override
    public ReviewDto mapEntityToDto(ReviewEntity entity) {
        return Objects.isNull(entity) ? null : ReviewDto.builder()
                .id(entity.getId())
                .client(userMapper.mapEntityToDto(entity.getClient()))
                .orderDto(orderMapper.mapEntityToDto(entity.getOrder()))
                .text(entity.getText())
                .build();
    }

    @Override
    public ReviewEntity mapDtoToEntity(ReviewDto dto) {
        return Objects.isNull(dto) ? null : ReviewEntity.builder()
                .id(dto.getId())
                .client(userMapper.mapDtoToEntity(dto.getClient()))
                .order(orderMapper.mapDtoToEntity(dto.getOrderDto()))
                .text(dto.getText())
                .build();
    }
}
