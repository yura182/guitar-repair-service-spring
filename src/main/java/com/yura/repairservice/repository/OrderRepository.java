package com.yura.repairservice.repository;

import com.yura.repairservice.domain.order.Status;
import com.yura.repairservice.entity.OrderEntity;
import com.yura.repairservice.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    Page<OrderEntity> findAllByMasterId(Integer id, Pageable pageable);

    Page<OrderEntity> findAllByClientId(Integer id, Pageable pageable);

    Page<OrderEntity> findAllByStatus(Status status, Pageable pageable);

    Long countByMasterId(Integer id);

    Long countByClientId(Integer id);

    Long countByStatus(Status status);

    @Modifying
    @Transactional
    @Query("UPDATE OrderEntity e SET e.status = :status WHERE e.id = :id")
    void updateOrderStatus(@Param("id") Integer id,
                           @Param("status") Status status);

    @Modifying
    @Transactional
    @Query("UPDATE OrderEntity e SET e.status = :status, e.rejectionReason = :rejectionReason WHERE e.id = :id")
    void updateOrderStatus(@Param("id") Integer id,
                     @Param("status") Status status,
                     @Param("rejectionReason") String rejectionReason);

    @Modifying
    @Transactional
    @Query("UPDATE OrderEntity e SET e.status = :status, e.master = :master WHERE e.id = :id")
    void updateOrderMaster(@Param("id") Integer id,
                           @Param("master") UserEntity master,
                           @Param("status") Status status);

    @Modifying
    @Transactional
    @Query("UPDATE OrderEntity e SET e.price = :price WHERE e.id = :id")
    void updateOrderPrice(@Param("id") Integer id,
                          @Param("price") Double price);


}
