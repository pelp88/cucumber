package ru.coderiders.cucumber.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.coderiders.cucumber.enums.SoilType;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

/**
 * Исходящее DTO участка
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldRsDto {
    @Schema(description = "Идентификатор карточки поля")
    private Long id;
    @Schema(description = "Ответное dto карточки растения")
    private PlantRsDto plant;
    @Schema(description = "Тип почвы")
    private SoilType soilType;
    @Schema(description = "Широта местоположения участка")
    private Double latitude;
    @Schema(description = "Долгота местоположения участка")
    private Double longitude;
    @Schema(description = "sensor_serial_number")
    private UUID sensorSerialNumber;
    @Schema(description = "жизни растения")
    private Integer plantHealth = 100;
}
