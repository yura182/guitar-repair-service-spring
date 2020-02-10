package com.yura.repair.service.impl;

import com.yura.repair.dto.InstrumentDto;
import com.yura.repair.repository.InstrumentRepository;
import com.yura.repair.service.InstrumentService;
import com.yura.repair.service.mapper.InstrumentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class InstrumentServiceImpl implements InstrumentService {
    private final InstrumentRepository instrumentRepository;
    private final InstrumentMapper instrumentMapper;

    @Override
    public void add(InstrumentDto instrumentDto) {
        instrumentRepository.save(instrumentMapper.mapDtoToEntity(instrumentDto));
    }
}
