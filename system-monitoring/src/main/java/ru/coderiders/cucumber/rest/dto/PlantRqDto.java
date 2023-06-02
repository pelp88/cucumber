package ru.coderiders.cucumber.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.coderiders.cucumber.enums.SoilType;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * Входное dto растения
 *
 * @author Gleb Luchinkin
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantRqDto {
    @NotNull
    @Schema(description = "Название растения")
    private String name;
    @NotNull
    @Schema(description = "Минимальная температура для посадки")
    private Integer minTemp;
    @NotNull
    @Schema(description = "Максимальная температура для посадки")
    private Integer maxTemp;
    @NotNull
    @Schema(description = "Тип почвы")
    private List<SoilType> soilType;
    @NotNull
    @Schema(description = "Дата посева")
    private LocalDate plantingDate;
    @NotNull
    @Schema(description = "Дата всхода")
    private LocalDate ripeningDate;
    @NotNull
    @Schema(description = "Сорт растения")
    private String plantSort;
    @NotNull
    @Schema(description = "Минимальная влажность")
    private Double minHumidity;
    @NotNull
    @Schema(description = "Максимальная влажность")
    private Double maxHumidity;
    @NotNull
    @Schema(description = "Минимальный уровень удобрений")
    private Double manure;
    @NotNull
    @Schema(description = "Минимальный уровень химикатов")
    private Double chemical;
}
