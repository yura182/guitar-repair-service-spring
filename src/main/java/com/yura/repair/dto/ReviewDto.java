package com.yura.repair.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    @NotNull
    private LocalDateTime date;

    @NotBlank(message = "Please enter text")
    private String text;
}


