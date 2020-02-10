package com.yura.repair.service.impl;

import com.yura.repair.dto.ReviewDto;
import com.yura.repair.repository.ReviewRepository;
import com.yura.repair.service.ReviewService;
import com.yura.repair.service.mapper.ReviewMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public void add(ReviewDto reviewDto) {
        reviewRepository.save(reviewMapper.mapDtoToEntity(reviewDto));
    }

    @Override
    public List<ReviewDto> findAll(Pageable pageable) {
        return reviewRepository
                .findAll(pageable)
                .stream()
                .map(reviewMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> findByOrder(Integer orderId, Pageable pageable) {
        return reviewRepository
                .findAllByOrderId(orderId, pageable)
                .stream()
                .map(reviewMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public long numberOfEntries() {
        return reviewRepository.count();
    }

    @Override
    public long numberOfEntriesByOrder(Integer orderId) {
        return reviewRepository.countByOrderId(orderId);
    }
}
