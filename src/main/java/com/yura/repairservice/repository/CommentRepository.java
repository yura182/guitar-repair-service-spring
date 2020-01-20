package com.yura.repairservice.repository;

import com.yura.repairservice.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    Page<CommentEntity> findAllByOrderId(Integer id, Pageable pageable);

    long countByOrderId(Integer id);
}
