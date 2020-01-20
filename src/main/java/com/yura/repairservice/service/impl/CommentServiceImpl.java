package com.yura.repairservice.service.impl;

import com.yura.repairservice.domain.order.Comment;
import com.yura.repairservice.entity.CommentEntity;
import com.yura.repairservice.repository.CommentRepository;
import com.yura.repairservice.service.CommentService;
import com.yura.repairservice.service.mapper.CommentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public void add(Comment comment) {
        commentRepository.save(commentMapper.mapCommentToCommentEntity(comment));
    }

    @Override
    public List<Comment> findAll(Pageable pageable) {
        return commentRepository
                .findAll(pageable)
                .stream()
                .map(commentMapper::mapCommentEntityToComment)
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> findByOrder(Integer orderId, Pageable pageable) {
        return commentRepository
                .findAllByOrderId(orderId, pageable)
                .stream()
                .map(commentMapper::mapCommentEntityToComment)
                .collect(Collectors.toList());
    }

    @Override
    public long numberOfEntries() {
        return commentRepository.count();
    }

    @Override
    public long numberOfEntriesByOrder(Integer orderId) {
        return commentRepository.countByOrderId(orderId);
    }
}
