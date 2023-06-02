package ru.coderiders.cucumber.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.coderiders.cucumber.rest.api.FieldApi;
import ru.coderiders.cucumber.rest.dto.FieldRqDto;
import ru.coderiders.cucumber.rest.dto.FieldRsDto;
import ru.coderiders.cucumber.rest.dto.PlantRsDto;
import ru.coderiders.cucumber.service.FieldService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для работы с участками
 *
 * @author Artyom Nikiforov - pelp88@outlook.com
 */

@Valid
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearer")
public class FieldController implements FieldApi {

    private final FieldService fieldService;

    @Override
    public ResponseEntity<FieldRsDto> create(FieldRqDto fieldRqDto) {
        var createdField = fieldService.create(fieldRqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdField);
    }

    @Override
    public Page<FieldRsDto> findAll(Pageable pageable) {
        return fieldService.findAll(pageable);
    }

    @Override
    public FieldRsDto update(Long fieldId, FieldRqDto fieldRqDto) {
        return fieldService.update(fieldId, fieldRqDto);
    }

    @Override
    public FieldRsDto findById(Long fieldId) {
        return fieldService.findById(fieldId);
    }

    @Override
    public ResponseEntity<Void> delete(Long fieldId) {
        fieldService.delete(fieldId);

        return ResponseEntity.accepted().build();
    }

    @Override
    public FieldRsDto putPlantOnField(Long idPlant, Long idField) {
        return fieldService.putPlantOnField(idPlant, idField);
    }

    @Override
    public FieldRsDto digOutPlantOnField(Long idField) {
        return fieldService.digOutPlantOnField(idField);
    }

    @Override
    public List<PlantRsDto> findAllowedPlants(Long idField) {
        return fieldService.getPlantsAllowedToPlaceOnField(idField);
    }
}
