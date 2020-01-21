package com.yura.repairservice.domain.instrument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Instrument {
    private Integer id;

    @NotEmpty(message = "Please enter brand of your instrument")
    private String brand;

    @NotEmpty(message = "Please enter model of your instrument")
    private String model;

    @Pattern(regexp = "[0-9]{4}", message = "Please enter valid year")
    private String manufactureYear;
}
