package com.yura.repair.repository;

import com.yura.repair.entity.InstrumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepository extends JpaRepository<InstrumentEntity, Integer> {
}
