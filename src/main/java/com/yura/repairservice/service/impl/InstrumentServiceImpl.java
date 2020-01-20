package com.yura.repairservice.service.impl;

import com.yura.repairservice.domain.instrument.Instrument;
import com.yura.repairservice.repository.InstrumentRepository;
import com.yura.repairservice.service.InstrumentService;
import com.yura.repairservice.service.mapper.InstrumentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Objects;

@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class InstrumentServiceImpl implements InstrumentService {
    private final InstrumentRepository instrumentRepository;
    private final InstrumentMapper instrumentMapper;

    @Override
    public void add(Instrument instrument) {
        instrumentRepository.save(instrumentMapper.mapInstrumentToInstrumentEntity(instrument));
    }


}
