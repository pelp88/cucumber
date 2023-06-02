package ru.coderiders.cucumber_emulator.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.coderiders.cucumber_emulator.rest.dto.SensorRqDto;
import ru.coderiders.cucumber_emulator.rest.dto.SensorRsDto;
import ru.coderiders.cucumber_emulator.entity.Sensor;

import javax.annotation.PostConstruct;

@AllArgsConstructor
@Component
public class SensorMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.createTypeMap(Sensor.class, SensorRsDto.class);
        modelMapper.createTypeMap(SensorRqDto.class, Sensor.class);
    }

    public Sensor toEntity(SensorRqDto sensorRqDto){
        return modelMapper.map(sensorRqDto, Sensor.class);
    }

    public SensorRsDto toDto(Sensor sensor){
        return modelMapper.map(sensor, SensorRsDto.class);
    }
}
