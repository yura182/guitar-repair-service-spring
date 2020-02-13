package com.yura.repair.service.impl;

import com.yura.repair.dto.InstrumentDto;
import com.yura.repair.entity.InstrumentEntity;
import com.yura.repair.repository.InstrumentRepository;
import com.yura.repair.service.mapper.EntityMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = InstrumentServiceImpl.class)
public class InstrumentServiceImplTest {
    private static final InstrumentDto INSTRUMENT_DTO = getInstrumentDto();
    private static final InstrumentEntity INSTRUMENT_ENTITY = getInstrumentEntity();

    @MockBean
    private InstrumentRepository instrumentRepository;

    @MockBean
    private EntityMapper<InstrumentEntity, InstrumentDto> instrumentMapper;

    @Autowired
    private InstrumentServiceImpl instrumentService;

    @Test
    public void addShouldAddInstrument() {
        when(instrumentMapper.mapDtoToEntity(INSTRUMENT_DTO)).thenReturn(INSTRUMENT_ENTITY);

        instrumentService.add(INSTRUMENT_DTO);

        verify(instrumentRepository).save(INSTRUMENT_ENTITY);
    }

    private static InstrumentDto getInstrumentDto() {
        return InstrumentDto.builder()
                .brand("Cort")
                .model("123")
                .manufactureYear("1995")
                .build();
    }

    private static InstrumentEntity getInstrumentEntity() {
        return InstrumentEntity.builder()
                .id(1)
                .brand("Cort")
                .model("123")
                .manufactureYear(1995)
                .build();
    }
}