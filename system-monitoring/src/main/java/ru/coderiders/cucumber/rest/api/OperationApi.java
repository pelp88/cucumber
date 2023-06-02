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
import ru.coderiders.cucumber.rest.dto.OperationRqDto;
import ru.coderiders.cucumber.rest.dto.OperationRsDto;
import ru.coderiders.cucumber.rest.dto.PlantRqDto;
import ru.coderiders.cucumber.rest.dto.PlantRsDto;

import javax.validation.Valid;

@RequestMapping("/operation")
@Tag(name = "Operation controller", description = "Контроллер переодических операций")
@Validated
public interface OperationApi {
    @Operation(
            summary = "Создание переодических операций",
            description = "Позволяет создать операцию, которая будет переодически выполняться в указанное время",
            method = "POST"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation =  OperationRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
    })
    @PostMapping("/create")
    ResponseEntity<OperationRsDto> create(@RequestBody @Valid OperationRqDto operationRqDto);

    @Operation(
            summary = "Получение всех операций",
            description = "Получение всех операций",
            method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping
    Page<OperationRsDto> findAll(@ParameterObject Pageable pageable);

    @Operation(
            summary = "Изменение операции",
            description = "Позволяет изменить значения в операции",
            method = "PUT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OperationRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @PutMapping("/{id}")
    OperationRsDto update(@Parameter(required = true, description = "Идентификатор") @PathVariable(name = "id") Long id,
                      @RequestBody @Valid OperationRqDto operationRqDto);

    @Operation(summary = "Получение операции",
            description = "Позволяет получить операцию по идентификатору",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OperationRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
    })
    @GetMapping("/{id}")
    OperationRsDto findById(@Parameter(required = true, description = "Идентификатор") @PathVariable(name = "id") Long id);

    @Operation(summary = "Удаление операции",
            description = "Позволяет удалить операцию по идентификатору",
            method = "DELETE")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@Parameter(required = true, description = "Идентификатор")
                                @PathVariable(name = "id") Long id);
}
