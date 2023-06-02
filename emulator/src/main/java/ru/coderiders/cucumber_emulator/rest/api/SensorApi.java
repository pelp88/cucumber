package ru.coderiders.cucumber_emulator.rest.api;


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
import ru.coderiders.cucumber_emulator.rest.dto.SensorRqDto;
import ru.coderiders.cucumber_emulator.rest.dto.SensorRsDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Rest-API для работы с датчиками
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */

@RequestMapping("/sensor")
@Tag(name = "Sensor Controller", description = "Контроллер участков")
@Validated
public interface SensorApi {
    @Operation(summary = "Создание датчика", description = "Позволяет создать датчик", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SensorRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
    })
    @PostMapping("/create")
    ResponseEntity<SensorRsDto> create(@RequestBody @Valid SensorRqDto sensorRqDto);

    @Operation(summary = "Получение всех датчиков", description = "Получение всех датчиков", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping
    Page<SensorRsDto> findAll(@ParameterObject Pageable pageable);

    @Operation(
            summary = "Изменение датчика",
            description = "Позволяет изменить информацию о датчике",
            method = "PUT"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SensorRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @PutMapping("/{sensorId}")
    SensorRsDto update(@PathVariable Long sensorId, @RequestBody @NotNull @Valid SensorRqDto sensorRqDto);

    @Operation(summary = "Получение датчика",
            description = "Позволяет получить датчик по идентификатору",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SensorRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
    })
    @GetMapping("/{sensorId}")
    SensorRsDto findById(@PathVariable Long sensorId);

    @Operation(summary = "Удаление датчика",
            description = "Позволяет удалить датчик по идентификатору",
            method = "DELETE")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @DeleteMapping("/{sensorId}")
    ResponseEntity<Void> delete(@PathVariable Long sensorId);
}
