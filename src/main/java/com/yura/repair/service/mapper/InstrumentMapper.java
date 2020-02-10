package com.yura.repair.service.mapper;

import com.yura.repair.dto.InstrumentDto;
import com.yura.repair.entity.InstrumentEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class InstrumentMapper implements EntityMapper<InstrumentEntity, InstrumentDto> {
    @Override
    public InstrumentDto mapEntityToDto(InstrumentEntity entity) {
        return Objects.isNull(entity) ? null : InstrumentDto.builder()
                .id(entity.getId())
                .brand(entity.getBrand())
                .model(entity.getModel())
                .manufactureYear(String.valueOf(entity.getManufactureYear()))
                .build();
    }

    @Override
    public InstrumentEntity mapDtoToEntity(InstrumentDto dto) {
        return Objects.isNull(dto) ? null : InstrumentEntity.builder()
                .id(dto.getId())
                .brand(dto.getBrand())
                .model(dto.getModel())
                .manufactureYear(Integer.parseInt(dto.getManufactureYear()))
                .build();
    }
}
