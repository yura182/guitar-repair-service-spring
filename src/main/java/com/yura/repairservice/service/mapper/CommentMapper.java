package com.yura.repairservice.service.mapper;

import com.yura.repairservice.domain.order.Comment;
import com.yura.repairservice.entity.CommentEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class CommentMapper {
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;

    public Comment mapCommentEntityToComment(CommentEntity commentEntity) {
        return Objects.isNull(commentEntity) ? null : Comment.builder()
                .id(commentEntity.getId())
                .client(userMapper.mapUserEntityToUser(commentEntity.getClient()))
                .order(orderMapper.mapOrderEntityToOrder(commentEntity.getOrder()))
                .text(commentEntity.getText())
                .build();
    }

    public CommentEntity mapCommentToCommentEntity(Comment comment) {
        return Objects.isNull(comment) ? null : CommentEntity.builder()
                .id(comment.getId())
                .client(userMapper.mapUserToUserEntity(comment.getClient()))
                .order(orderMapper.mapOrderToOrderEntity(comment.getOrder()))
                .text(comment.getText())
                .build();
    }
}
