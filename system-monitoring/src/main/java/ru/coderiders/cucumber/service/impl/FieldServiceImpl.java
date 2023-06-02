package ru.coderiders.cucumber.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.coderiders.cucumber.enums.SoilType;
import ru.coderiders.cucumber.enums.TaskStatus;
import ru.coderiders.cucumber.mapper.FieldMapper;
import ru.coderiders.cucumber.mapper.PlantMapper;
import ru.coderiders.cucumber.repository.FieldRepository;
import ru.coderiders.cucumber.repository.PlantRepository;
import ru.coderiders.cucumber.repository.TaskRepository;
import ru.coderiders.cucumber.rest.dto.FieldRqDto;
import ru.coderiders.cucumber.rest.dto.FieldRsDto;
import ru.coderiders.cucumber.rest.dto.PlantRsDto;
import ru.coderiders.cucumber.rest.dto.ReadingDtoForCreating;
import ru.coderiders.cucumber.rest.dto.TaskRqDto;
import ru.coderiders.cucumber.rest.exception.CannotBePlantedException;
import ru.coderiders.cucumber.rest.exception.NotFoundException;
import ru.coderiders.cucumber.service.FieldService;
import ru.coderiders.cucumber.service.TaskService;
import ru.coderiders.cucumber.util.BeanUtilsHelper;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервис для работы с участками
 *
 * @author Artyom Nikiforov - pelp88@outlook.com
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {

    private static final String FIELD_IS_NOT_FOUND_BY_ID_MSG = "Участок с id = %d не найден";
    private static final String PLANT_IS_NOT_FOUND_BY_ID_MSG = "Растение с id = %d не найдено";
    private static final String PLANT_CANNOT_BE_PLACED_MSG = "Типы почв растения и участка не совпадают";
    private static final String PLANT_ALREADY_PLACED_MSG = "Типы на этом участке уже растет растение";
    private static final String[] IGNORED_ON_COPY_FIELDS = {"id"};

    private final TaskService taskService;
    private final FieldRepository fieldRepository;
    private final FieldMapper fieldMapper;
    private final TaskRepository taskRepository;
    private final PlantRepository plantRepository;
    private final PlantMapper plantMapper;
    private final KafkaTemplate<Long, ReadingDtoForCreating> kafkaTemplate;

    @Override
    @Transactional
    public FieldRsDto create(FieldRqDto fieldRqDto) {
        if (isPlantCannotBePlaced(fieldRqDto.getPlant(), fieldRqDto.getSoilType())) {
            throw new CannotBePlantedException(PLANT_CANNOT_BE_PLACED_MSG);
        }
        var field = fieldMapper.toEntity(fieldRqDto);

        Optional.ofNullable(fieldRqDto.getPlant())
                .flatMap(plantRepository::findById)
                .ifPresent(field::setPlant);
        var created = fieldRepository.save(field);

        ReadingDtoForCreating readingDtoForCreating = new ReadingDtoForCreating();
        readingDtoForCreating.setLatitude(created.getLatitude());
        readingDtoForCreating.setLongitude(created.getLongitude());
        readingDtoForCreating.setSensorSerialNumber(created.getSensorSerialNumber());
        kafkaTemplate.send("sensor.creating", readingDtoForCreating);
        return fieldMapper.toDto(created);
    }

    @Override
    @Transactional
    public Page<FieldRsDto> findAll(Pageable pageable) {
        return fieldRepository.findAll(pageable)
                .map(fieldMapper::toDto);
    }

    @Override
    @Transactional
    public FieldRsDto findById(@NotNull Long id) {
        return fieldRepository.findById(id)
                .map(fieldMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(FIELD_IS_NOT_FOUND_BY_ID_MSG, id)));

    }

    @Override
    public List<FieldRsDto> getFieldsByPlantId(Long plantId) {
        return fieldRepository.getFieldsByPlantId(plantId)
                .stream()
                .map(fieldMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public FieldRsDto update(Long id, FieldRqDto fieldRqDto) {
        if (isPlantCannotBePlaced(fieldRqDto.getPlant(), fieldRqDto.getSoilType())) {
            throw new CannotBePlantedException(PLANT_CANNOT_BE_PLACED_MSG);
        }
        return fieldRepository.findById(id)
                .map(field -> {
                    var newField = fieldMapper.toEntity(fieldRqDto);

                    Optional.ofNullable(fieldRqDto.getPlant())
                            .flatMap(plantRepository::findById)
                            .ifPresent(field::setPlant);

                    BeanUtils.copyProperties(newField, field,
                            BeanUtilsHelper.getNullPropertyNames(newField, IGNORED_ON_COPY_FIELDS));
                    return field;
                })
                .map(fieldMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(FIELD_IS_NOT_FOUND_BY_ID_MSG, id)));
    }

    @Override
    @Transactional
    public void delete(@NotNull Long id) {
        fieldRepository.findById(id).ifPresent(fieldRepository::delete);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public FieldRsDto putPlantOnField(Long idPlant, Long idField) {
        return fieldRepository.findById(idField)
                .map(field -> {

                    if (field.getPlant() != null) {
                        throw new CannotBePlantedException(PLANT_ALREADY_PLACED_MSG);
                    }

                    field.setPlant(plantRepository.findById(idPlant)
                            .orElseThrow(
                                    () -> new NotFoundException(String.format(PLANT_IS_NOT_FOUND_BY_ID_MSG, idPlant))
                            ));

                    if (isPlantCannotBePlaced(idPlant, field.getSoilType())) {
                        throw new CannotBePlantedException(PLANT_CANNOT_BE_PLACED_MSG);
                    }

                    field.setPlantHealth(100);

                    BeanUtils.copyProperties(field, field,
                            BeanUtilsHelper.getNullPropertyNames(field, IGNORED_ON_COPY_FIELDS));

                    return field;
                })
                .map(fieldMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(FIELD_IS_NOT_FOUND_BY_ID_MSG, idField)));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public FieldRsDto digOutPlantOnField(Long idField) {
        return fieldRepository.findById(idField)
                .map(field -> {
                    field.setPlant(null);
                    BeanUtils.copyProperties(field, field,
                            BeanUtilsHelper.getNullPropertyNames(field, IGNORED_ON_COPY_FIELDS));

                    return field;
                })
                .map(fieldMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(FIELD_IS_NOT_FOUND_BY_ID_MSG, idField)));
    }

    @Override
    public Optional<FieldRsDto> findFieldByUuidAndPlantNotNull(UUID uuid) {
        return fieldRepository.findFieldBySensorSerialNumberAndPlantNotNull(uuid)
                .map(fieldMapper::toDto);
    }

    @Override
    public List<PlantRsDto> getPlantsAllowedToPlaceOnField(Long id) {
        FieldRsDto foundedField = findById(id);
        return plantRepository.findPlantsBySoilTypeContaining(foundedField.getSoilType()).stream()
                .map(plantMapper::toDto)
                .collect(Collectors.toList());
    }

    private boolean isPlantCannotBePlaced(Long idPlant, SoilType fieldSoilType) {
        if (idPlant == 0) {
            return false;
        }
        return !plantRepository.findById(idPlant)
                .orElseThrow(() -> new NotFoundException(String.format(PLANT_IS_NOT_FOUND_BY_ID_MSG, idPlant)))
                .getSoilType()
                .contains(fieldSoilType);
    }

    @Transactional
    @Scheduled(cron = "2 * * * * *")
    public void fade() {
        taskRepository.findByStatus(TaskStatus.OPEN).stream()
                .filter(task -> task.getStartTime().isBefore(LocalDateTime.now()))
                .forEach(task -> {
                    var field = task.getField();
                    if (field.getPlantHealth() > 0) {
                        field.setPlantHealth(field.getPlantHealth() - 10);
                        fieldRepository.save(field);
                    } else {
                        taskRepository.getAllByField_Id(task.getField().getId())
                                .stream().filter(task1 -> !task1.getType().equals("Выкорчевать растение"))
                                .forEach(taskToClose -> {
                                    taskToClose.setStatus(TaskStatus.CLOSED);
                                    taskToClose.setEndTime(LocalDateTime.now());
                                    taskRepository.save(taskToClose);
                                });
                        taskService.create(TaskRqDto.builder()
                                .field(task.getField().getId())
                                .status(TaskStatus.OPEN)
                                .type("Выкорчевать растение")
                                .startTime(LocalDateTime.now())
                                .build());
                    }
                });
    }
}