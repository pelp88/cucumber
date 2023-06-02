package ru.coderiders.cucumber.rest.api;

import io.swagger.v3.oas.annotations.Operation;
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
import ru.coderiders.cucumber.rest.dto.FieldRqDto;
import ru.coderiders.cucumber.rest.dto.FieldRsDto;
import ru.coderiders.cucumber.rest.dto.PlantRsDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Rest-API для работы с участками
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */

@RequestMapping("/field")
@Tag(name = "Field Controller", description = "Контроллер участков")
@Validated
public interface FieldApi {
    @Operation(summary = "Создание карточки грядки", description = "Позволяет создать карточку грядки", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FieldRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
    })
    @PostMapping("/create")
    ResponseEntity<FieldRsDto> create(@RequestBody @Valid FieldRqDto fieldRqDto);

    @Operation(summary = "Получение карточек всех грядок", description = "Получение карточек всех грядок", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping
    Page<FieldRsDto> findAll(@ParameterObject Pageable pageable);

    @Operation(
            summary = "Изменение карточки грядки",
            description = "Позволяет изменить информацию о карточке грядки",
            method = "PUT"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FieldRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @PutMapping("/{fieldId}")
    FieldRsDto update(@PathVariable Long fieldId, @RequestBody @NotNull @Valid FieldRqDto fieldRqDto);

    @Operation(summary = "Получение карточки грядки",
            description = "Позволяет получить карточку грядки по идентификатору",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FieldRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
    })
    @GetMapping("/{fieldId}")
    FieldRsDto findById(@PathVariable Long fieldId);

    @Operation(summary = "Удаление карточки грядки",
            description = "Позволяет удалить карточку грядки по идентификатору",
            method = "DELETE")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @DeleteMapping("/{fieldId}")
    ResponseEntity<Void> delete(@PathVariable Long fieldId);

    @Operation(
            summary = "Добавление в карточку грядки расстения",
            description = "Позволяет в карточку грядки растение, которое будет расти",
            method = "PUT"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FieldRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @PutMapping("/put-plant/{idPlant}/{idField}")
    FieldRsDto putPlantOnField(@PathVariable Long idPlant, @PathVariable Long idField);

    @Operation(
            summary = "Удаление растения из карточки участка",
            description = "Позволяет удалить растение из карточки участка",
            method = "PUT"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FieldRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @PutMapping("/dig-out-plant/{idField}")
    FieldRsDto digOutPlantOnField(@PathVariable Long idField);

    @Operation(
            summary = "Поиск растений возможных для посадки",
            description = "Позволяет найти растения, которые возможно высадить на поле",
            method = "GET"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping("/allowed-plants/{idField}")
    List<PlantRsDto> findAllowedPlants(@NotNull @PathVariable Long idField);
}
