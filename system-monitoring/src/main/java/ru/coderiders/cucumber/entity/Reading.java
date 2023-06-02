package ru.coderiders.cucumber.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность показаний
 *
 * @author Gleb Luchinkin
 */

@Entity
@Getter
@Setter
@Table(name = "reading")
@AllArgsConstructor
@NoArgsConstructor
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "pg-uuid")
    @Column(name = "serial_number")
    private UUID serialNumber;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "air_temperature")
    private Double airTemperature;

    @Column(name = "humidity")
    private Integer humidity;

    @Column(name = "pressure")
    private Integer pressure;

    @Column(name = "precipitation_amount")
    private Double precipitationAmount;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "chemical_level")
    private Double chemicalLevel;

    @Column(name = "manure_level")
    private Double manureLevel;
}
