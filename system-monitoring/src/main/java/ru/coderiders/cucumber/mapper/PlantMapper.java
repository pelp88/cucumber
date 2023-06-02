package ru.coderiders.cucumber.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.coderiders.cucumber.entity.Plant;
import ru.coderiders.cucumber.rest.dto.PlantRqDto;
import ru.coderiders.cucumber.rest.dto.PlantRsDto;

import javax.annotation.PostConstruct;

/**
 * Маппер карточки растения
 *
 * @author Lucinkin Gleb
 */
@Component
@AllArgsConstructor
public class PlantMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.createTypeMap(PlantRqDto.class, Plant.class);
        modelMapper.createTypeMap(Plant.class, PlantRsDto.class);
    }

    public Plant toEntity(PlantRqDto plantRqDto) {
        return modelMapper.map(plantRqDto, Plant.class);
    }

    public PlantRsDto toDto(Plant plant) {
        return modelMapper.map(plant, PlantRsDto.class);
    }
}
