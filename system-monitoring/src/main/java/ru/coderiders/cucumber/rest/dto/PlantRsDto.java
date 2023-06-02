package ru.coderiders.cucumber.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.coderiders.cucumber.enums.SoilType;

import java.time.LocalDate;
import java.util.List;

/**
 * Ответное dto растения
 *
 * @author Gleb Luchinkin
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantRsDto {
    @Schema(description = "Идентификатор культуры")
    private Long id;
    @Schema(description = "Название растения")
    private String name;
    @Schema(description = "Минимальная температура для посадки")
    private Integer minTemp;
    @Schema(description = "Максимальная температура для посадки")
    private Integer maxTemp;
    @Schema(description = "Тип почвы")
    private List<SoilType> soilType;
    @Schema(description = "Дата посева")
    private LocalDate plantingDate;
    @Schema(description = "Дата всхода")
    private LocalDate ripeningDate;
    @Schema(description = "Сорт растения")
    private String plantSort;
    @Schema(description = "Процент оставшихся удобрений")
    private Double manure;
    @Schema(description = "Процент оставшихся химикатов")
    private Double chemical;
    @Schema(description = "Минимальная влажность")
    private Double minHumidity;
    @Schema(description = "Максимальная влажность")
    private Double maxHumidity;
}
