package ru.coderiders.cucumber.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.coderiders.cucumber.enums.SoilType;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;

/**
 * Сущность растения
 *
 * @author Gleb Luchinkin
 */

@Entity
@Getter
@Setter
@Table(name = "plants")
@AllArgsConstructor
@NoArgsConstructor
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "min_temp")
    private Integer minTemp;

    @Column(name = "max_temp")
    private Integer maxTemp;

    @Column(name = "soil_type")
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<SoilType> soilType;

    @Column(name = "planting_date")
    private LocalDate plantingDate;

    @Column(name = "ripening_date")
    private LocalDate ripeningDate;

    @Column(name = "plant_sort")
    private String plantSort;

    @Min(0)
    @Max(100)
    @Column(name = "manure")
    private Double manure;

    @Min(0)
    @Max(100)
    @Column(name = "chemical")
    private Double chemical;

    @Column(name = "min_humidity")
    private Double minHumidity;

    @Column(name = "max_humidity")
    private Double maxHumidity;
}
