package ru.coderiders.cucumber_emulator.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorRsDto {
    @Schema(description = "ID датчика (в таблице)")
    private Long id;

    @Schema(description = "UUID датчика (уникальный идент.)")
    private UUID serialNumber;

    @Schema(description = "Смещение температуры для генератора значений")
    private Double tempOffset;

    @Schema(description = "Смещение уровня влажности для генератора значений")
    private Integer humidityOffset;

    @Schema(description = "Смещение давления для генератора значений")
    private Integer pressureOffset;

    @Schema(description = "Смещение уровня осадков для генератора значений")
    private Double precipitationAmountOffset;

    @Schema(description = "Смещение скорости ветра для генератора значений")
    private Double windSpeedOffset;

    @Schema(description = "Смещение уровня химикатов")
    private Double chemicalOffset;

    @Schema(description = "Смещение уровня удобрения")
    private Double manureOffset;

    @Schema(description = "Уровень химикатов (для генерации)")
    private Double chemicalLevel;

    @Schema(description = "Уровень удобрения (для генерации)")
    private Double manureLevel;

    @Schema(description = "Погодная геолокация по широте для генератора")
    private Double latitude;

    @Schema(description = "Погодная геолокация по долготе для генератора")
    private Double longitude;
}
