package ru.coderiders.cucumber_emulator.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Исходящее DTO погоды
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class ReadingDto extends AbstractDto{
    @Schema(description = "Дата и время получения данных")
    private LocalDateTime time;

    @Schema(description = "Серийный номер датчика")
    private UUID serialNumber;

    @Schema(description = "Температура воздуха")
    private Double airTemperature;

    @Schema(description = "Влажность в процентах")
    private Integer humidity;

    @Schema(description = "Атмосферное давление в гПа")
    private Integer pressure;

    @Schema(description = "Кол-во осадков в мм.")
    private Double precipitationAmount;

    @Schema(description = "Скорость ветра в м/с")
    private Double windSpeed;

    @Schema(description = "Уровень химикатов в почве")
    private Double chemicalLevel;

    @Schema(description = "Уровень удобренности почвы")
    private Double manureLevel;
}
