package com.yura.repair.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstrumentDto {
    private Integer id;

    @NotBlank(message = "Please enter brand of your instrument")
    private String brand;

    @NotBlank(message = "Please enter model of your instrument")
    private String model;

    @Pattern(regexp = "[0-9]{4}", message = "Please enter valid year")
    private String manufactureYear;
}
