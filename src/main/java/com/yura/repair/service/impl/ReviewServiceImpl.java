package com.yura.repair.service.impl;

import com.yura.repair.dto.ReviewDto;
import com.yura.repair.entity.ReviewEntity;
import com.yura.repair.repository.ReviewRepository;
import com.yura.repair.service.ReviewService;
import com.yura.repair.service.mapper.EntityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final EntityMapper<ReviewEntity, ReviewDto> reviewMapper;

    @Override
    public void add(ReviewDto reviewDto) {
        reviewRepository.save(reviewMapper.mapDtoToEntity(reviewDto));
    }

    @Override
    public Page<ReviewDto> findAll(Pageable pageable) {
        return reviewRepository
                .findAll(pageable)
                .map(reviewMapper::mapEntityToDto);
    }

    @Override
    public void delete(ReviewDto reviewDto) {
        reviewRepository.delete(reviewMapper.mapDtoToEntity(reviewDto));
    }


}
