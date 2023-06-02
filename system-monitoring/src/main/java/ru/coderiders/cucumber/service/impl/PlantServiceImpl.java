package ru.coderiders.cucumber.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.coderiders.cucumber.enums.TaskStatus;
import ru.coderiders.cucumber.mapper.PlantMapper;
import ru.coderiders.cucumber.repository.FieldRepository;
import ru.coderiders.cucumber.repository.PlantRepository;
import ru.coderiders.cucumber.repository.TaskRepository;
import ru.coderiders.cucumber.rest.dto.PlantRqDto;
import ru.coderiders.cucumber.rest.dto.PlantRsDto;
import ru.coderiders.cucumber.rest.dto.TaskRqDto;
import ru.coderiders.cucumber.rest.exception.NotFoundException;
import ru.coderiders.cucumber.service.PlantService;
import ru.coderiders.cucumber.service.TaskService;
import ru.coderiders.cucumber.util.BeanUtilsHelper;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Сервис дря работы c растениями
 *
 * @author Gleb Luchinkin
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {

    private static final String PLANT_IS_NOT_FOUND_BY_ID_MSG = "Культура с id = %d не найдена";
    private static final String[] IGNORED_ON_COPY_FIELDS = {"id"};

    private final PlantRepository plantRepository;
    private final PlantMapper plantMapper;

    @Override
    @Transactional
    public PlantRsDto create(PlantRqDto plantRqDto) {
        var plant = plantMapper.toEntity(plantRqDto);
        var created = plantRepository.save(plant);
        return plantMapper.toDto(created);
    }

    @Override
    @Transactional
    public Page<PlantRsDto> findAll(Pageable pageable) {
        return plantRepository.findAll(pageable)
                .map(plantMapper::toDto);
    }

    @Override
    @Transactional
    public PlantRsDto findById(@NotNull Long id) {
        return plantRepository.findById(id)
                .map(plantMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(PLANT_IS_NOT_FOUND_BY_ID_MSG, id)));

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PlantRsDto update(Long id, PlantRqDto plantRqDto) {
        var update = plantRepository.findById(id)
                .map(plant -> {

                    var newPlant = plantMapper.toEntity(plantRqDto);

                    BeanUtils.copyProperties(newPlant, plant, BeanUtilsHelper.getNullPropertyNames(newPlant,
                            IGNORED_ON_COPY_FIELDS));
                    return plant;
                })
                .orElseThrow(() -> new NotFoundException(String.format(PLANT_IS_NOT_FOUND_BY_ID_MSG, id)));
        return plantMapper.toDto(update);
    }

    @Override
    @Transactional
    public void delete(@NotNull Long id) {
        plantRepository.findById(id).ifPresent(plantRepository::delete);
    }
}
