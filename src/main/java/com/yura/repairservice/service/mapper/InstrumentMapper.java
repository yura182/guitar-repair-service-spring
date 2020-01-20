package com.yura.repairservice.service.mapper;

import com.yura.repairservice.domain.instrument.Instrument;
import com.yura.repairservice.entity.InstrumentEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class InstrumentMapper {
    public Instrument mapInstrumentEntityToInstrument(InstrumentEntity instrumentEntity) {
        return Objects.isNull(instrumentEntity) ? null : Instrument.builder()
                .id(instrumentEntity.getId())
                .brand(instrumentEntity.getBrand())
                .model(instrumentEntity.getModel())
                .manufactureYear(String.valueOf(instrumentEntity.getManufactureYear()))
                .condition(instrumentEntity.getCondition())
                .build();
    }

    public InstrumentEntity mapInstrumentToInstrumentEntity(Instrument instrument) {
        return Objects.isNull(instrument) ? null : InstrumentEntity.builder()
                .id(instrument.getId())
                .brand(instrument.getBrand())
                .model(instrument.getModel())
                .manufactureYear(Integer.parseInt(instrument.getManufactureYear()))
                .condition(instrument.getCondition())
                .build();
    }
}
