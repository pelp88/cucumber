package ru.coderiders.cucumber.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.coderiders.cucumber.enums.SoilType;

import javax.validation.constraints.NotNull;

/**
 * Входящее DTO участка
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldRqDto {
    @Schema(description = "Идентификатор карточки растения")
    private Long plant;
    @NotNull
    @Schema(description = "Тип почвы")
    private SoilType soilType;
    @NotNull
    @Schema(description = "Широта местоположения участка")
    private Double latitude;
    @NotNull
    @Schema(description = "Долгота местоположения участка")
    private Double longitude;
}
