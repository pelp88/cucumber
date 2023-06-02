package ru.coderiders.cucumber.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.coderiders.cucumber.entity.Plant;
import ru.coderiders.cucumber.enums.RegularOperation;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationRsDto {
    @Schema(description = "Идентификатор операции")
    private Long id;
    @Schema(description = "Идентификатор растения")
    private PlantRsDto plantId;
    @Schema(description = "Название операции")
    private RegularOperation operationName;
    @Schema(description = "Дата, при достижении которой должна создасться задача")
    private LocalDate procDate;
    @Schema(description = "Интервал через который будет создаваться задача. Задается в днях")
    private Long interval;
}
