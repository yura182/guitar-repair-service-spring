package com.yura.repair.service.impl;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.ReviewDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.OrderEntity;
import com.yura.repair.entity.ReviewEntity;
import com.yura.repair.entity.UserEntity;
import com.yura.repair.repository.ReviewRepository;
import com.yura.repair.service.mapper.EntityMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ReviewServiceImpl.class)
public class ReviewServiceImplTest {
    private static final ReviewDto REVIEW_DTO = getReviewDto();
    private static final ReviewEntity REVIEW_ENTITY = getReviewEntity();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private EntityMapper<ReviewEntity, ReviewDto> reviewMapper;

    @Autowired
    private ReviewServiceImpl reviewService;

    @Test
    public void addShouldAddReview() {
        when(reviewMapper.mapDtoToEntity(REVIEW_DTO)).thenReturn(REVIEW_ENTITY);

        reviewService.add(REVIEW_DTO);

        verify(reviewRepository).save(REVIEW_ENTITY);
    }

    @Test
    public void deleteShouldDeleteReview() {
        when(reviewMapper.mapDtoToEntity(REVIEW_DTO)).thenReturn(REVIEW_ENTITY);
        reviewService.delete(REVIEW_DTO);

        verify(reviewRepository).delete(REVIEW_ENTITY);
    }

    @Test
    public void findAllShouldReturnPageOfReviews() {
        Page<ReviewDto> expected = new PageImpl<>(Collections.singletonList(REVIEW_DTO));
        Page<ReviewEntity> reviewEntities = new PageImpl<>(Collections.singletonList(REVIEW_ENTITY));
        Pageable pageable = PageRequest.of(1, 1);

        when(reviewRepository.findAll(pageable)).thenReturn(reviewEntities);
        when(reviewMapper.mapEntityToDto(REVIEW_ENTITY)).thenReturn(REVIEW_DTO);

        Page<ReviewDto> actual = reviewService.findAll(pageable);

        assertEquals(expected, actual);
    }

    @Test
    public void findAllShouldReturnEmptyPage() {
        Page<ReviewDto> expected = Page.empty();
        Page<ReviewEntity> reviewEntities = Page.empty();
        Pageable pageable = PageRequest.of(1, 1);

        when(reviewRepository.findAll(pageable)).thenReturn(reviewEntities);
        when(reviewMapper.mapEntityToDto(REVIEW_ENTITY)).thenReturn(REVIEW_DTO);

        Page<ReviewDto> actual = reviewService.findAll(pageable);

        assertEquals(expected, actual);
    }

    private static ReviewDto getReviewDto() {
        return ReviewDto.builder()
                .client(UserDto.builder().name("Yura").build())
                .orderDto(OrderDto.builder().service("Service").build())
                .date(LocalDateTime.of(1990, 12, 12, 12, 12))
                .text("Text")
                .build();
    }

    private static ReviewEntity getReviewEntity() {
        return ReviewEntity.builder()
                .client(UserEntity.builder().id(1).name("Yura").build())
                .order(OrderEntity.builder().id(1).service("Service").build())
                .date(LocalDateTime.of(1990, 12, 12, 12, 12))
                .text("Text")
                .build();
    }
}