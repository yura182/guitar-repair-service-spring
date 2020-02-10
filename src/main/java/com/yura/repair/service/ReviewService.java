package com.yura.repair.service;

import com.yura.repair.dto.ReviewDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    void add(ReviewDto reviewDto);

    List<ReviewDto> findAll(Pageable pageable);

    List<ReviewDto> findByOrder(Integer orderId, Pageable pageable);

    long numberOfEntries();

    long numberOfEntriesByOrder(Integer orderId);
}
