package ru.coderiders.cucumber_emulator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.coderiders.cucumber_emulator.entity.Sensor;
import ru.coderiders.cucumber_emulator.mapper.SensorMapper;
import ru.coderiders.cucumber_emulator.repository.SensorRepository;
import ru.coderiders.cucumber_emulator.rest.dto.SensorRqDto;
import ru.coderiders.cucumber_emulator.rest.dto.SensorRsDto;
import ru.coderiders.cucumber_emulator.rest.exception.NotFoundException;
import ru.coderiders.cucumber_emulator.service.SensorService;
import ru.coderiders.cucumber_emulator.util.BeanUtilsHelper;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {
    private static final String SENSOR_IS_NOT_FOUND_BY_ID_MSG = "Датчик с id = %d не найден";

    private static final String[] IGNORED_ON_COPY_FIELDS = {"id"};

    private final SensorMapper sensorMapper;

    private final SensorRepository sensorRepository;

    @Override
    @Transactional
    public SensorRsDto create(SensorRqDto sensorRqDto) {
        var sensor = sensorRepository.save(sensorMapper.toEntity(sensorRqDto));
        return sensorMapper.toDto(sensor);
    }

    @Override
    public void createWithValuesFromKafka(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    @Override
    @Transactional
    public Page<SensorRsDto> findAll(Pageable pageable) {
        return sensorRepository.findAll(pageable)
                .map(sensorMapper::toDto);
    }

    @Override
    @Transactional
    public SensorRsDto update(Long sensorId, SensorRqDto sensorRqDto) {
        var update = sensorRepository.findById(sensorId)
                .map(sensor -> {
                    var newSensor = sensorMapper.toEntity(sensorRqDto);
                    BeanUtils.copyProperties(newSensor, sensor,
                            BeanUtilsHelper.getNullPropertyNames(newSensor,
                                    IGNORED_ON_COPY_FIELDS));
                    return sensor;
                })
                .orElseThrow(() -> new NotFoundException(String
                        .format(SENSOR_IS_NOT_FOUND_BY_ID_MSG, sensorId)));
        return sensorMapper.toDto(update);
    }

    @Override
    @Transactional
    public SensorRsDto findById(Long id) {
        return sensorRepository.findById(id)
                .map(sensorMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String
                        .format(SENSOR_IS_NOT_FOUND_BY_ID_MSG, id)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        sensorRepository.findById(id)
                .ifPresent(sensorRepository::delete);
    }
}
