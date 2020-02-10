package com.yura.repair.service.mapper;

import com.yura.repair.dto.ReviewDto;
import com.yura.repair.entity.CommentEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class ReviewMapper {
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;

    public ReviewDto mapCommentEntityToComment(CommentEntity commentEntity) {
        return Objects.isNull(commentEntity) ? null : ReviewDto.builder()
                .id(commentEntity.getId())
                .client(userMapper.mapUserEntityToUser(commentEntity.getClient()))
                .orderDto(orderMapper.mapOrderEntityToOrder(commentEntity.getOrder()))
                .text(commentEntity.getText())
                .build();
    }

    public CommentEntity mapCommentToCommentEntity(ReviewDto reviewDto) {
        return Objects.isNull(reviewDto) ? null : CommentEntity.builder()
                .id(reviewDto.getId())
                .client(userMapper.mapUserToUserEntity(reviewDto.getClient()))
                .order(orderMapper.mapOrderToOrderEntity(reviewDto.getOrderDto()))
                .text(reviewDto.getText())
                .build();
    }
}
