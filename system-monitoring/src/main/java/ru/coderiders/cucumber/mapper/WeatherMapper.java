package ru.coderiders.cucumber.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.coderiders.cucumber.entity.Weather;
import ru.coderiders.cucumber.rest.dto.WeatherRqDto;
import ru.coderiders.cucumber.rest.dto.WeatherRsDto;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class WeatherMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.createTypeMap(Weather.class, WeatherRsDto.class);
        modelMapper.createTypeMap(WeatherRqDto.class, Weather.class)
                .addMappings(mapping -> mapping.skip(Weather::setFieldId));
    }

    public Weather toEntity(WeatherRqDto weatherRqDto) {
        return modelMapper.map(weatherRqDto, Weather.class);
    }

    public WeatherRsDto toDto(Weather weather) {
        return modelMapper.map(weather, WeatherRsDto.class);
    }
}
