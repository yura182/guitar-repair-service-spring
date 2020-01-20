package com.yura.repairservice.domain.order;

import com.yura.repairservice.domain.user.User;
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
public class Comment {
    private Integer id;

    @NotNull
    private User client;

    @NotNull
    private Order order;

    @NotEmpty(message = "Please enter text")
    private String text;
}


