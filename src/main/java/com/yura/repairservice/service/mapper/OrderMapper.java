package com.yura.repairservice.service.mapper;

import com.yura.repairservice.domain.order.Order;
import com.yura.repairservice.domain.user.User;
import com.yura.repairservice.entity.OrderEntity;
import com.yura.repairservice.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderMapper {
    private final UserMapper userMapper;
    private final InstrumentMapper instrumentMapper;

    public Order mapOrderEntityToOrder(OrderEntity orderEntity) {
        return Objects.isNull(orderEntity) ? null : Order.builder()
                .id(orderEntity.getId())
                .client(userMapper.mapUserEntityToUser(orderEntity.getClient()))
                .master(userMapper.mapUserEntityToUser(orderEntity.getMaster()))
                .instrument(instrumentMapper.mapInstrumentEntityToInstrument(orderEntity.getInstrument()))
                .dateTime(orderEntity.getDateTime())
                .service(orderEntity.getService())
                .price(orderEntity.getPrice())
                .status(orderEntity.getStatus())
                .rejectionReason(orderEntity.getRejectionReason())
                .build();
    }

    public OrderEntity mapOrderToOrderEntity(Order order) {
        return Objects.isNull(order) ? null : OrderEntity.builder()
                .id(order.getId())
                .client(userMapper.mapUserToUserEntity(order.getClient()))
                .master(userMapper.mapUserToUserEntity(order.getMaster()))
                .instrument(instrumentMapper.mapInstrumentToInstrumentEntity(order.getInstrument()))
                .dateTime(order.getDateTime())
                .service(order.getService())
                .price(order.getPrice())
                .status(order.getStatus())
                .rejectionReason(order.getRejectionReason())
                .build();
    }

    public OrderEntity mapOrderToOrderEntity(Order order, User master) {
        return Objects.isNull(order) || Objects.isNull(master) ? null : OrderEntity.builder()
                .id(order.getId())
                .master(UserEntity.builder()
                        .id(master.getId())
                        .build())
                .build();
    }
}
