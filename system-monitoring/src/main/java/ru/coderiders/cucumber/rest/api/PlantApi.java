package ru.coderiders.cucumber.rest.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.coderiders.cucumber.enums.SoilType;
import ru.coderiders.cucumber.rest.dto.PlantRqDto;
import ru.coderiders.cucumber.rest.dto.PlantRsDto;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest-API для работы с культурами
 *
 * @author Gleb Lucinkin
 */

@RequestMapping("/plant")
@Tag(name = "Plant controller", description = "Контроллер культуры")
@Validated
public interface PlantApi {
    @Operation(
            summary = "Создание карточки культуры",
            description = "Позволяет создать карточку культуры",
            method = "POST"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PlantRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
    })
    @PostMapping("/create")
    ResponseEntity<PlantRsDto> create(@RequestBody @Valid PlantRqDto plantRqDto);

    @Operation(
            summary = "Получение карточек всех культур",
            description = "Получение карточек всех культур",
            method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping
    Page<PlantRsDto> findAll(@ParameterObject Pageable pageable);

    @Operation(
            summary = "Изменение карточки культуры",
            description = "Позволяет изменить информацию в карточке кульуры",
            method = "PUT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PlantRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @PutMapping("/{id}")
    PlantRsDto update(@Parameter(required = true, description = "Идентификатор") @PathVariable(name = "id") Long Id,
                      @RequestBody @Valid PlantRqDto plantRqDto);

    @Operation(summary = "Получение карточки культуры",
            description = "Позволяет получить карточку культуры по идентификатору",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PlantRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
    })
    @GetMapping("/{id}")
    PlantRsDto findById(@Parameter(required = true, description = "Идентификатор") @PathVariable(name = "id") Long id);

    @Operation(summary = "Удаление карточки культуры",
            description = "Позволяет удалить карточку культуры по идентификатору",
            method = "DELETE")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@Parameter(required = true, description = "Идентификатор")
                                @PathVariable(name = "id") Long id);

    @Operation(
            summary = "Получение типов почв у культуры",
            description = "Получение типов почв, на которых возможно высадить культуру",
            method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping("/soil_type/{id}")
    List<SoilType> findSoilTypeByPlantId(@PathVariable(name = "id") Long id);
}
