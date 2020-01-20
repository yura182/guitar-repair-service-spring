package com.yura.repairservice.repository;

import com.yura.repairservice.entity.InstrumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepository extends JpaRepository<InstrumentEntity, Integer> {
}
