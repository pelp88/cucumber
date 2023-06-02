package ru.coderiders.cucumber.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.coderiders.cucumber.enums.SoilType;
import ru.coderiders.cucumber.rest.api.PlantApi;
import ru.coderiders.cucumber.rest.dto.PlantRqDto;
import ru.coderiders.cucumber.rest.dto.PlantRsDto;
import ru.coderiders.cucumber.service.PlantService;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для работы с культурами
 *
 * @author Gleb Lucinkin
 */

@Valid
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearer")
public class PlantController implements PlantApi {

    private final PlantService plantService;

    @Override
    public ResponseEntity<PlantRsDto> create(PlantRqDto plantRqDto) {
        var createdPlant = plantService.create(plantRqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlant);
    }

    @Override
    public Page<PlantRsDto> findAll(Pageable pageable) {
        return plantService.findAll(pageable);
    }

    @Override
    public PlantRsDto update(Long plantId, PlantRqDto plantRqDto) {
        return plantService.update(plantId, plantRqDto);
    }

    @Override
    public PlantRsDto findById(Long id) {
        return plantService.findById(id);
    }

    @Override
    public ResponseEntity<Void> delete(Long plantId) {
        plantService.delete(plantId);
        return ResponseEntity.accepted().build();
    }

    @Override
    public List<SoilType> findSoilTypeByPlantId(Long id) {
        return plantService.findById(id).getSoilType();
    }
}
