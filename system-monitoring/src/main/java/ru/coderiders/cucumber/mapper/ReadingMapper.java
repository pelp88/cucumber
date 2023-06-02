package ru.coderiders.cucumber.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.coderiders.cucumber.entity.Reading;
import ru.coderiders.cucumber.rest.dto.ReadingDto;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class ReadingMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.createTypeMap(ReadingDto.class, Reading.class);
        modelMapper.createTypeMap(Reading.class, ReadingDto.class);
    }

    public Reading toEntity(ReadingDto readingDto) {
        return modelMapper.map(readingDto, Reading.class);
    }

    public ReadingDto toDto(Reading reading) {
        return modelMapper.map(reading, ReadingDto.class);
    }
}
