package com.yura.repairservice.service;

import com.yura.repairservice.domain.order.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    void add(Comment comment);

    List<Comment> findAll(Pageable pageable);

    List<Comment> findByOrder(Integer orderId, Pageable pageable);

    long numberOfEntries();

    long numberOfEntriesByOrder(Integer orderId);
}
