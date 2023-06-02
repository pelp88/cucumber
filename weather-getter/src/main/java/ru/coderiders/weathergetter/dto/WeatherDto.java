package ru.coderiders.weathergetter.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Входящее DTO погоды
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonDeserialize(using = WeatherDtoDeserializer.class)
public class WeatherDto {
    @NotNull
    @Schema(description = "ID участка")
    private Long field;
    @NotNull
    @Schema(description = "Дата и время погодных данных")
    private LocalDateTime time;
    @NotNull
    @Schema(description = "Температура воздуха")
    private Double airTemperature;
    @NotNull
    @Schema(description = "Влажность в процентах")
    private Integer humidity;
    @NotNull
    @Schema(description = "Атмосферное давление в гПа")
    private Integer pressure;
    @NotNull
    @Schema(description = "Описание погоды")
    private String description;
    @NotNull
    @Schema(description = "Облачность в процентах")
    private Integer cloudinessPercent;
    @NotNull
    @Schema(description = "Вероятность грозы")
    private Double stormPrediction;
    @NotNull
    @Schema(description = "Кол-во осадков в мм.")
    private Double precipitationAmount;
    @NotNull
    @Schema(description = "Тип осадков")
    private String precipitationType;
    @NotNull
    @Schema(description = "Код погодного явления (в соотв. с API OWM)")
    private Integer phenomenonCode;
    @NotNull
    @Schema(description = "ID погодной иконки (в соотв. с API OWM)")
    private String iconId;
    @NotNull
    @Schema(description = "Скорость ветра в м/с")
    private Double windSpeed;
}
