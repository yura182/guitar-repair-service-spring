package com.yura.repair.dto;

import com.yura.repair.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Integer id;
    private UserDto client;
    private UserDto master;
    private InstrumentDto instrumentDto;
    private LocalDateTime dateTime;

    @NotBlank(message = "Please enter service for your instrument")
    private String service;

    private Double price;
    private Status status;
    private String rejectionReason;

}
