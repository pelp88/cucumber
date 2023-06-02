package ru.coderiders.cucumber.rest.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.coderiders.cucumber.rest.dto.UserReportDto;

import java.time.LocalDate;

@RequestMapping("/report")
@Tag(name = "Reports controller", description = "Контроллер отчетов")
@Validated
public interface ReportAPI {

    @Operation(summary = "Получение отчета по вполненным работам у сотрудника",
            description = "Позволяет получить отчет по вполненным работам у сотрудника",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserReportDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
    })
    @GetMapping("/{id}")
    UserReportDto getUserReport(@PathVariable Long id, @RequestParam(name = "firstDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate firstDate,
                                @RequestParam(name = "secondDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate secondDate);
}
