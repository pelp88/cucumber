package ru.coderiders.cucumber.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.coderiders.cucumber.entity.Field;

import java.time.LocalDateTime;

/**
 * Исходящее DTO погоды
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherRsDto {

    @Schema(description = "ID прогноза погоды")
    private Long id;

    @Schema(description = "ID участка")
    private Field field;

    @Schema(description = "Дата и время погодных данных")
    private LocalDateTime time;

    @Schema(description = "Температура воздуха")
    private Double airTemperature;

    @Schema(description = "Влажность в процентах")
    private Integer humidity;

    @Schema(description = "Атмосферное давление в гПа")
    private Integer pressure;

    @Schema(description = "Описание погоды")
    private String description;

    @Schema(description = "Облачность в процентах")
    private Integer cloudinessPercent;

    @Schema(description = "Вероятность грозы")
    private Integer stormPrediction;

    @Schema(description = "Кол-во осадков в мм.")
    private Double precipitationAmount;

    @Schema(description = "Тип осадков")
    private String precipitationType;

    @Schema(description = "Код погодного явления (в соотв. с API OWM)")
    private Double phenomenonCode;

    @Schema(description = "ID погодной иконки (в соотв. с API OWM)")
    private String iconId;

    @Schema(description = "Скорость ветра в м/с")
    private Double windSpeed;
}
