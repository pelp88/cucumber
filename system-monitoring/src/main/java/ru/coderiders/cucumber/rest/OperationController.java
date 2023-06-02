package ru.coderiders.cucumber.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.coderiders.cucumber.rest.api.OperationApi;
import ru.coderiders.cucumber.rest.dto.OperationRqDto;
import ru.coderiders.cucumber.rest.dto.OperationRsDto;
import ru.coderiders.cucumber.service.OperationService;

import javax.validation.Valid;

@Valid
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearer")
public class OperationController implements OperationApi {

    private final OperationService operationService;

    @Override
    public ResponseEntity<OperationRsDto> create(OperationRqDto operationRqDto) {
        var createdPlant = operationService.create(operationRqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlant);
    }

    @Override
    public Page<OperationRsDto> findAll(Pageable pageable) {
        return operationService.findAll(pageable);
    }

    @Override
    public OperationRsDto update(Long id, OperationRqDto operationRqDto) {
        return operationService.update(id, operationRqDto);
    }

    @Override
    public OperationRsDto findById(Long id) {
        return operationService.findById(id);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        operationService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
