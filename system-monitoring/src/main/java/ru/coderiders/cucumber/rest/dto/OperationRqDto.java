package ru.coderiders.cucumber.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.coderiders.cucumber.enums.RegularOperation;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationRqDto {
    @NotNull
    @Schema(description = "Идентификатор растения")
    private Long plantId;
    @NotNull
    @Schema(description = "Название операции")
    private RegularOperation operationName;
    @NotNull
    @Schema(description = "Интервал через который будет создаваться задача. Задается в днях")
    private Long interval;
}
