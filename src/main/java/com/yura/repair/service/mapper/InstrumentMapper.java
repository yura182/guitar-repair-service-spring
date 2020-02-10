package com.yura.repair.service.mapper;

import com.yura.repair.dto.InstrumentDto;
import com.yura.repair.entity.InstrumentEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class InstrumentMapper {
    public InstrumentDto mapInstrumentEntityToInstrument(InstrumentEntity instrumentEntity) {
        return Objects.isNull(instrumentEntity) ? null : InstrumentDto.builder()
                .id(instrumentEntity.getId())
                .brand(instrumentEntity.getBrand())
                .model(instrumentEntity.getModel())
                .manufactureYear(String.valueOf(instrumentEntity.getManufactureYear()))
                .build();
    }

    public InstrumentEntity mapInstrumentToInstrumentEntity(InstrumentDto instrumentDto) {
        return Objects.isNull(instrumentDto) ? null : InstrumentEntity.builder()
                .id(instrumentDto.getId())
                .brand(instrumentDto.getBrand())
                .model(instrumentDto.getModel())
                .manufactureYear(Integer.parseInt(instrumentDto.getManufactureYear()))
                .build();
    }
}
