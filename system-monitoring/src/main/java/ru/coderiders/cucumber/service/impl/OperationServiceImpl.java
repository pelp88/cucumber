package ru.coderiders.cucumber.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.coderiders.cucumber.mapper.OperationMapper;
import ru.coderiders.cucumber.repository.OperationRepository;
import ru.coderiders.cucumber.repository.PlantRepository;
import ru.coderiders.cucumber.rest.dto.OperationRqDto;
import ru.coderiders.cucumber.rest.dto.OperationRsDto;
import ru.coderiders.cucumber.rest.exception.NotFoundException;
import ru.coderiders.cucumber.service.OperationService;
import ru.coderiders.cucumber.util.BeanUtilsHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private static final String PLANT_IS_NOT_FOUND_BY_ID_MSG = "Растение с id = %d не найдено";
    private static final String OPERATION_IS_NOT_FOUND_BY_ID_MSG = "Операция с id = %d не найдено";
    private static final String[] IGNORED_ON_COPY_FIELDS = {"id"};
    private final PlantRepository plantRepository;
    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;

    @Override
    @Transactional
    public OperationRsDto create(OperationRqDto operationRqDto) {

        var plant = plantRepository.findById(operationRqDto.getPlantId())
                .orElseThrow(() -> new NotFoundException(String.format(PLANT_IS_NOT_FOUND_BY_ID_MSG
                        , operationRqDto.getPlantId()))
                );

        var operation = operationMapper.toEntity(operationRqDto);
        operation.setProcDate(plant.getPlantingDate().plusDays(operationRqDto.getInterval()));
        operation.setPlantId(plant);

        var created = operationRepository.save(operation);
        return operationMapper.toDto(created);
    }

    @Override
    @Transactional
    public Page<OperationRsDto> findAll(Pageable pageable) {
        return operationRepository.findAll(pageable)
                .map(operationMapper::toDto);
    }

    @Override
    public List<OperationRsDto> getAllOperationsByProcDate(LocalDate date) {
        return operationRepository.getAllOperationsByProcDate(date)
                .stream()
                .map(operationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OperationRsDto findById(Long id) {
        return operationRepository.findById(id)
                .map(operationMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(OPERATION_IS_NOT_FOUND_BY_ID_MSG, id)));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OperationRsDto update(Long id, OperationRqDto operationRqDto) {
        var update = operationRepository.findById(id)
                .map(operation -> {

                    var newPlantingDate = plantRepository.findById(operationRqDto.getPlantId())
                            .orElseThrow(() -> new NotFoundException(String.format(PLANT_IS_NOT_FOUND_BY_ID_MSG, id)))
                            .getPlantingDate().plusDays(operationRqDto.getInterval());

                    operation.setProcDate(newPlantingDate);
                    operation.setOperationName(operationRqDto.getOperationName());
                    operation.setInterval(operationRqDto.getInterval());

                    BeanUtils.copyProperties(operation, operation, BeanUtilsHelper.getNullPropertyNames(operation,
                            IGNORED_ON_COPY_FIELDS));
                    return operation;
                })
                .orElseThrow(() -> new NotFoundException(String.format(PLANT_IS_NOT_FOUND_BY_ID_MSG, id)));
        return operationMapper.toDto(update);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        operationRepository.findById(id).ifPresent(operationRepository::delete);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OperationRsDto changeProcDate(Long id, LocalDate newProcDate) {
        var update = operationRepository.findById(id)
                .map(operation -> {
                    operation.setProcDate(newProcDate);
                    BeanUtils.copyProperties(operation, operation, BeanUtilsHelper.getNullPropertyNames(operation,
                            IGNORED_ON_COPY_FIELDS));
                    return operation;
                })
                .orElseThrow(() -> new NotFoundException(String.format(PLANT_IS_NOT_FOUND_BY_ID_MSG, id)));
        return operationMapper.toDto(update);
    }
}
