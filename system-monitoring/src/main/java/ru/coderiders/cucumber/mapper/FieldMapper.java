package ru.coderiders.cucumber.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.coderiders.cucumber.entity.Field;
import ru.coderiders.cucumber.rest.dto.FieldRqDto;
import ru.coderiders.cucumber.rest.dto.FieldRsDto;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class FieldMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.createTypeMap(FieldRqDto.class, Field.class)
                .addMappings(mapping -> mapping.skip(Field::setPlant));
        modelMapper.createTypeMap(Field.class, FieldRsDto.class);
        modelMapper.createTypeMap(FieldRsDto.class, FieldRqDto.class)
                .addMappings(mapping -> mapping.map(fieldRsDto -> fieldRsDto.getPlant().getId(), FieldRqDto::setPlant));

    }

    public Field toEntity(FieldRqDto fieldRqDto) {
        return modelMapper.map(fieldRqDto, Field.class);
    }

    public FieldRsDto toDto(Field field) {
        return modelMapper.map(field, FieldRsDto.class);
    }

    public FieldRqDto toRqDto(FieldRsDto fieldRsDto) {
        return modelMapper.map(fieldRsDto, FieldRqDto.class);
    }
}
