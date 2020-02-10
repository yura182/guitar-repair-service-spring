package com.yura.repair.service.mapper;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.entity.OrderEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderMapper implements EntityMapper<OrderEntity, OrderDto> {
    private final UserMapper userMapper;
    private final InstrumentMapper instrumentMapper;

    @Override
    public OrderDto mapEntityToDto(OrderEntity entity) {
        return Objects.isNull(entity) ? null : OrderDto.builder()
                .id(entity.getId())
                .client(userMapper.mapEntityToDto(entity.getClient()))
                .master(userMapper.mapEntityToDto(entity.getMaster()))
                .instrumentDto(instrumentMapper.mapEntityToDto(entity.getInstrument()))
                .dateTime(entity.getDateTime())
                .service(entity.getService())
                .price(entity.getPrice())
                .status(entity.getStatus())
                .rejectionReason(entity.getRejectionReason())
                .build();
    }

    @Override
    public OrderEntity mapDtoToEntity(OrderDto dto) {
        return Objects.isNull(dto) ? null : OrderEntity.builder()
                .id(dto.getId())
                .client(userMapper.mapDtoToEntity(dto.getClient()))
                .master(userMapper.mapDtoToEntity(dto.getMaster()))
                .instrument(instrumentMapper.mapDtoToEntity(dto.getInstrumentDto()))
                .dateTime(dto.getDateTime())
                .service(dto.getService())
                .price(dto.getPrice())
                .status(dto.getStatus())
                .rejectionReason(dto.getRejectionReason())
                .build();
    }
}
