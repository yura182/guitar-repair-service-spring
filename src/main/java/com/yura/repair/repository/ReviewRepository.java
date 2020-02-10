package com.yura.repair.repository;

import com.yura.repair.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    Page<ReviewEntity> findAllByOrderId(Integer id, Pageable pageable);

    long countByOrderId(Integer id);
}
