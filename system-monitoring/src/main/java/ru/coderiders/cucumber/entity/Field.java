package ru.coderiders.cucumber.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.coderiders.cucumber.enums.SoilType;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

/**
 * Сущность участка
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */

@Entity
@Getter
@Setter
@Table(name = "fields")
@AllArgsConstructor
@NoArgsConstructor
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_plant", referencedColumnName = "id")
    private Plant plant;

    @Column(name = "soil_type")
    @Enumerated(EnumType.STRING)
    private SoilType soilType;

    @Column(name = "latitude") // пусть будет формата "широта/долгота"
    private Double latitude;

    @Column(name = "longitude") // пусть будет формата "широта/долгота"
    private Double longitude;

    @Column(name = "sensor_serial_number")
    private UUID sensorSerialNumber = UUID.randomUUID();

    @Column(name = "plant_health")
    @Min(0)
    @Max(100)
    private Integer plantHealth = 100;
}