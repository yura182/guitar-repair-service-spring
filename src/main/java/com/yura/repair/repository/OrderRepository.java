package com.yura.repair.repository;

import com.yura.repair.entity.OrderEntity;
import com.yura.repair.entity.Status;
import com.yura.repair.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    Page<OrderEntity> findAllByMasterId(Integer id, Pageable pageable);

    Page<OrderEntity> findAllByClientId(Integer id, Pageable pageable);

    Page<OrderEntity> findAllByStatus(Status status, Pageable pageable);
}
