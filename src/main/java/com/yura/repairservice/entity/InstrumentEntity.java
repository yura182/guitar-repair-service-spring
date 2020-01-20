package com.yura.repairservice.entity;

import com.yura.repairservice.domain.instrument.Condition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "instruments")
public class InstrumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "brand", nullable = false, length = 45)
    private String brand;

    @Column(name = "model", nullable = false, length = 45)
    private String model;

    @Column(name = "manufacture_year")
    private Integer manufactureYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition", nullable = false, length = 45)
    private Condition condition;
}
