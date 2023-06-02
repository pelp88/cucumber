package ru.coderiders.cucumber_emulator.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorRqDto {
    @NotNull
    @Schema(description = "Смещение температуры для генератора значений")
    private Double tempOffset;

    @NotNull
    @Schema(description = "Смещение уровня влажности для генератора значений")
    private Integer humidityOffset;

    @NotNull
    @Schema(description = "Смещение давления для генератора значений")
    private Integer pressureOffset;

    @NotNull
    @Schema(description = "Смещение уровня осадков для генератора значений")
    private Double precipitationAmountOffset;

    @NotNull
    @Schema(description = "Смещение скорости ветра для генератора значений")
    private Double windSpeedOffset;

    @NotNull
    @Schema(description = "Смещение уровня химикатов")
    private Double chemicalOffset;

    @NotNull
    @Schema(description = "Смещение уровня удобрения")
    private Double manureOffset;

    @NotNull
    @Schema(description = "Погодная геолокация по широте для генератора")
    private Double latitude;

    @NotNull
    @Schema(description = "Погодная геолокация по долготе для генератора")
    private Double longitude;
}
