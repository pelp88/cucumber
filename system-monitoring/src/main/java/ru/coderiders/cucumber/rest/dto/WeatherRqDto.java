package ru.coderiders.cucumber.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherRqDto {
    @JsonProperty(value = "fieldId")
    @NotNull
    @Schema(description = "ID участка")
    private Long field;

    @JsonProperty(value = "time")
    @NotNull
    @Schema(description = "Дата и время погодных данных")
    private LocalDateTime time;

    @JsonProperty(value = "temperature")
    @NotNull
    @Schema(description = "Температура воздуха")
    private Double airTemperature;

    @JsonProperty(value = "humidity")
    @NotNull
    @Schema(description = "Влажность в процентах")
    private Integer humidity;

    @JsonProperty(value = "pressure")
    @NotNull
    @Schema(description = "Атмосферное давление в гПа")
    private Integer pressure;

    @JsonProperty(value = "description")
    @NotNull
    @Schema(description = "Описание погоды")
    private String description;

    @JsonProperty(value = "cloudiness")
    @NotNull
    @Schema(description = "Облачность в процентах")
    private Integer cloudinessPercent;

    @JsonProperty(value = "stormPrediction")
    @NotNull
    @Schema(description = "Вероятность грозы")
    private Double stormPrediction;

    @JsonProperty(value = "precipitationAmount")
    @NotNull
    @Schema(description = "Кол-во осадков в мм.")
    private Double precipitationAmount;

    @JsonProperty(value = "precipitationType")
    @NotNull
    @Schema(description = "Тип осадков")
    private String precipitationType;

    @JsonProperty(value = "phenomenonCode")
    @NotNull
    @Schema(description = "Код погодного явления (в соотв. с API OWM)")
    private Integer phenomenonCode;

    @JsonProperty(value = "iconId")
    @NotNull
    @Schema(description = "ID погодной иконки (в соотв. с API OWM)")
    private String iconId;

    @JsonProperty(value = "windSpeed")
    @NotNull
    @Schema(description = "Скорость ветра в м/с")
    private Double windSpeed;
}
