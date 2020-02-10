package com.yura.repair.repository;

import com.yura.repair.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<CommentEntity, Integer> {

    Page<CommentEntity> findAllByOrderId(Integer id, Pageable pageable);

    long countByOrderId(Integer id);
}
