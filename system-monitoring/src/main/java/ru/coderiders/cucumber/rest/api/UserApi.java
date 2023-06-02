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
import ru.coderiders.cucumber.rest.dto.UserRqDto;
import ru.coderiders.cucumber.rest.dto.UserRsDto;

import javax.validation.Valid;

/**
 * Rest-API для работы с пользователями
 *
 * @author Anton Belyakov
 */

@RequestMapping("/user")
@Tag(name = "User controller", description = "Контроллер пользователя")
@Validated
public interface UserApi {

    @Operation(
            summary = "Создание пользователя",
            description = "Позволяет создать пользователя",
            method = "POST"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
    })
    @PostMapping("/create")
    ResponseEntity<UserRsDto> create(@RequestBody @Valid UserRqDto userRqDto);

    @Operation(
            summary = "Получение всех пользователей",
            description = "Позволяет получить всех пользователей",
            method = "GET"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping
    Page<UserRsDto> findAll(@ParameterObject Pageable pageable);

    @Operation(
            summary = "Обновление пользователя",
            description = "Позволяет обновить информацию о пользователе",
            method = "PUT"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @PutMapping("/{userId}")
    UserRsDto update(@Parameter(required = true, description = "Идентификатор пользователя")
                     @PathVariable Long userId, @RequestBody @Valid UserRqDto userRqDto);

    @Operation(
            summary = "Получение пользователя",
            description = "Позволяет получить пользователя по идентификатору",
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
    })
    @GetMapping("/{userId}")
    UserRsDto findById(@Parameter(required = true, description = "Идентификатор пользователя")
                       @PathVariable Long userId);

    @Operation(
            summary = "Удаление пользователя",
            description = "Позволяет удалить пользователя по идентификатору",
            method = "DELETE"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @DeleteMapping("/{userId}")
    ResponseEntity<Void> delete(@Parameter(required = true, description = "Идентификатор пользователя")
                                @PathVariable Long userId);

    @Operation(
            summary = "Фильтрация пользователей по роли",
            description = "Позволяет отфильтровать пользователей, значение роли которых не содержит искомого значения",
            method = "GET"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
    })
    @GetMapping("/ru/coderiders/cucumber/filter/role/{role}")
    Page<UserRsDto> findAllByRole(@PathVariable String role, @ParameterObject Pageable pageable);

    @Operation(
            summary = "Фильтрация пользователей идентификатору",
            description = "Позволяет получить пользователей, значение идентификатора которых выше заданного значения",
            method = "GET"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
    })
    @GetMapping("/ru/coderiders/cucumber/filter/id-above/{number}")
    Page<UserRsDto> findAllThanAboveNumber(@PathVariable Long number, @ParameterObject Pageable pageable);

    @Operation(
            summary = "Фильтрация пользователей идентификатору",
            description = "Позволяет получить пользователей, значение идентификатора которых ниже заданного значения",
            method = "GET"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserRsDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
    })
    @GetMapping("/ru/coderiders/cucumber/filter/id-below/{number}")
    Page<UserRsDto> findAllThanBelowNumber(@PathVariable Long number, @ParameterObject Pageable pageable);
}
