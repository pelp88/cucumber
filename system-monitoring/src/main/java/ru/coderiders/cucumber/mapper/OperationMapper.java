package ru.coderiders.cucumber.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.coderiders.cucumber.entity.Operation;
import ru.coderiders.cucumber.rest.dto.OperationRqDto;
import ru.coderiders.cucumber.rest.dto.OperationRsDto;

import javax.annotation.PostConstruct;

/**
 * Маппер для переодических операций
 *
 * @author Anton Belyakov
 */
@Component
@AllArgsConstructor
public class OperationMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.createTypeMap(OperationRqDto.class, Operation.class);
        modelMapper.createTypeMap(Operation.class, OperationRsDto.class);
    }

    public Operation toEntity(OperationRqDto operationRqDto) {
        return modelMapper.map(operationRqDto, Operation.class);
    }

    public OperationRsDto toDto(Operation operation) {
        return modelMapper.map(operation, OperationRsDto.class);
    }
}
