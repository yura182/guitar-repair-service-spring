package com.yura.repair.service.mapper;

import com.yura.repair.dto.InstrumentDto;
import com.yura.repair.entity.InstrumentEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InstrumentMapperTest {
    private static final InstrumentDto INSTRUMENT_DTO_DTO = getInstrumentDtoDto();
    private static final InstrumentEntity INSTRUMENT_ENTITY = getInstrumentEntity();

    private final InstrumentMapper instrumentMapper = new InstrumentMapper();

    @Test
    public void mapEntityToDtoShouldReturnDto() {
        InstrumentDto actual = instrumentMapper.mapEntityToDto(INSTRUMENT_ENTITY);

        assertEquals(INSTRUMENT_DTO_DTO, actual);
    }

    @Test
    public void mapDtoToEntityShouldReturnEntity() {
        InstrumentEntity actual = instrumentMapper.mapDtoToEntity(INSTRUMENT_DTO_DTO);

        assertEquals(INSTRUMENT_ENTITY, actual);
    }

    @Test
    public void mapEntityToDtoShouldReturnNull() {
        InstrumentDto actual = instrumentMapper.mapEntityToDto(null);

        assertNull(actual);
    }

    @Test
    public void mapDtoToEntityShouldReturnNull() {
        InstrumentEntity actual = instrumentMapper.mapDtoToEntity(null);

        assertNull(actual);
    }

    private static InstrumentDto getInstrumentDtoDto() {
        return InstrumentDto.builder()
                .id(1)
                .brand("Brand")
                .model("Model")
                .manufactureYear("1990")
                .build();
    }

    private static InstrumentEntity getInstrumentEntity() {
        return InstrumentEntity.builder()
                .id(1)
                .brand("Brand")
                .model("Model")
                .manufactureYear(1990)
                .build();
    }
}