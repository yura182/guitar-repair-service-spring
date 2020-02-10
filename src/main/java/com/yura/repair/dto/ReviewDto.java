package com.yura.repair.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Integer id;

    @NotNull
    private UserDto client;

    @NotNull
    private OrderDto orderDto;

    @NotEmpty(message = "Please enter text")
    private String text;
}


