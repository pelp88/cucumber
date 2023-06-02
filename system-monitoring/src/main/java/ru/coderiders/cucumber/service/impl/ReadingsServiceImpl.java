package ru.coderiders.cucumber.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.coderiders.cucumber.entity.Reading;
import ru.coderiders.cucumber.mapper.ReadingMapper;
import ru.coderiders.cucumber.repository.ReadingRepository;
import ru.coderiders.cucumber.rest.dto.ReadingDto;
import ru.coderiders.cucumber.service.FieldService;
import ru.coderiders.cucumber.service.ReadingsService;
import ru.coderiders.cucumber.service.visitor.VisitorChain;

import javax.transaction.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class ReadingsServiceImpl implements ReadingsService {

    private final VisitorChain readingVisitorChain;
    private final ReadingRepository readingRepository;
    private final ReadingMapper readingMapper;
    private final FieldService fieldService;

    @Override
    @Transactional
    @KafkaListener(topics = "readings.created", groupId = "myGroup", containerFactory = "singleFactory")
    public void consumeReadings(ReadingDto readingDto) {
        generateTasks(readingRepository.save(readingMapper.toEntity(readingDto)));
    }

    public void generateTasks(Reading reading) {
        fieldService.findFieldByUuidAndPlantNotNull(reading.getSerialNumber())
                .ifPresent(fieldRsDto -> readingVisitorChain.visit(readingMapper.toDto(reading), fieldRsDto));
    }
}
