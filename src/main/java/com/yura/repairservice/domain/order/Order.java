package com.yura.repairservice.domain.order;

import com.yura.repairservice.domain.instrument.Instrument;
import com.yura.repairservice.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer id;
    private User client;
    private User master;
    private Instrument instrument;
    private LocalDateTime dateTime;

    @NotEmpty(message = "Please enter service for your instrument")
    private String service;

    private Double price;
    private Status status;
    private String rejectionReason;

}
