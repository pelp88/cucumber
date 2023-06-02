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
import ru.coderiders.cucumber.rest.dto.TaskRqDto;
import ru.coderiders.cucumber.rest.dto.TaskRsDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Rest-API для работы с работами
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */

@RequestMapping("/task")
@Tag(name = "Task Controller", description = "Контроллер работ")
@Validated
public interface TaskApi {
    @Operation(summary = "Создание задач", description = "Позволяет создать задачу", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TaskRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
    })
    @PostMapping("/create")
    ResponseEntity<TaskRsDto> create(@RequestBody @Valid TaskRqDto taskRqDto);

    @Operation(summary = "Получение всех задач", description = "Получение всех задач", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping
    Page<TaskRsDto> findAll(@ParameterObject Pageable pageable);

    @Operation(summary = "Изменение задачи", description = "Позволяет изменить информацию в задаче", method = "PUT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TaskRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @PutMapping("/{taskId}")
    TaskRsDto update(@PathVariable Long taskId, @RequestBody @NotNull @Valid TaskRqDto taskRqDto);

    @Operation(summary = "Получение задачи",
            description = "Позволяет получить задачу по идентификатору",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TaskRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
    })
    @GetMapping("/{taskId}")
    TaskRsDto findById(@PathVariable Long taskId);

    @Operation(
            summary = "Удаление задачи",
            description = "Позволяет удалить задачу по идентификатору",
            method = "DELETE")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @DeleteMapping("/{taskId}")
    ResponseEntity<Void> delete(@PathVariable Long taskId);

    @Operation(
            summary = "Назначение задачи на пользователя",
            description = "Позволяет назначить задачу на пользователя",
            method = "UPDATE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @PutMapping("/appoint/{taskId}/{userId}")
    TaskRsDto takeTaskByUser(@PathVariable @NotNull Long taskId, @PathVariable @NotNull Long userId);

    @Operation(
            summary = "Закрытие задачи",
            description = "Позволяет позволяет закрыть задачу при выполнении",
            method = "UPDATE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @PutMapping("/close/{taskId}")
    TaskRsDto closeTask(@PathVariable @NotNull Long taskId);
}
