package com.yura.repair.service.mapper;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.OrderEntity;
import com.yura.repair.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderMapper {
    private final UserMapper userMapper;
    private final InstrumentMapper instrumentMapper;

    public OrderDto mapOrderEntityToOrder(OrderEntity orderEntity) {
        return Objects.isNull(orderEntity) ? null : OrderDto.builder()
                .id(orderEntity.getId())
                .client(userMapper.mapUserEntityToUser(orderEntity.getClient()))
                .master(userMapper.mapUserEntityToUser(orderEntity.getMaster()))
                .instrumentDto(instrumentMapper.mapInstrumentEntityToInstrument(orderEntity.getInstrument()))
                .dateTime(orderEntity.getDateTime())
                .service(orderEntity.getService())
                .price(orderEntity.getPrice())
                .status(orderEntity.getStatus())
                .rejectionReason(orderEntity.getRejectionReason())
                .build();
    }

    public OrderEntity mapOrderToOrderEntity(OrderDto orderDto) {
        return Objects.isNull(orderDto) ? null : OrderEntity.builder()
                .id(orderDto.getId())
                .client(userMapper.mapUserToUserEntity(orderDto.getClient()))
                .master(userMapper.mapUserToUserEntity(orderDto.getMaster()))
                .instrument(instrumentMapper.mapInstrumentToInstrumentEntity(orderDto.getInstrumentDto()))
                .dateTime(orderDto.getDateTime())
                .service(orderDto.getService())
                .price(orderDto.getPrice())
                .status(orderDto.getStatus())
                .rejectionReason(orderDto.getRejectionReason())
                .build();
    }

    public OrderEntity mapOrderToOrderEntity(OrderDto orderDto, UserDto master) {
        return Objects.isNull(orderDto) || Objects.isNull(master) ? null : OrderEntity.builder()
                .id(orderDto.getId())
                .master(UserEntity.builder()
                        .id(master.getId())
                        .build())
                .build();
    }
}
