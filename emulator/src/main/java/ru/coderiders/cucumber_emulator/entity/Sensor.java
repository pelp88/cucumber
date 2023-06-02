package ru.coderiders.cucumber_emulator.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "sensors")
@AllArgsConstructor
@NoArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private UUID serialNumber = UUID.randomUUID();

    @Column(name = "temp_offset")
    private Double tempOffset;

    @Column(name = "humidity_offset")
    private Integer humidityOffset;

    @Column(name = "pressure_offset")
    private Integer pressureOffset;

    @Column(name = "precipitation_amount_offset")
    private Double precipitationAmountOffset;

    @Column(name = "wind_speed_offset")
    private Double windSpeedOffset;

    @Column(name = "chemical_offset")
    private Double chemicalOffset;

    @Column(name = "chemical_level")
    private Double chemicalLevel = 0.0;

    @Schema(name = "manure_offset")
    private Double manureOffset;

    @Column(name = "maunre_level")
    private Double manureLevel = 0.0;

    @Column(name = "latitude_weather_geolocation")
    private Double latitude;

    @Column(name = "longitude_weather_geolocation")
    private Double longitude;

}
