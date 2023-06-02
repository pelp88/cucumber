package ru.coderiders.cucumber.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Сущность погоды
 *
 * @author Gleb Luchinkin
 * @author Artyom Nikiforov
 */

@Entity
@Getter
@Setter
@Table(name = "weather")
@AllArgsConstructor
@NoArgsConstructor
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field fieldId;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "air_temperature")
    private Double airTemperature;

    @Column(name = "humidity")
    private Integer humidity;

    @Column(name = "pressure")
    private Integer pressure;

    @Column(name = "description")
    private String description;

    @Column(name = "cloudiness_percent")
    private Integer cloudinessPercent;

    @Column(name = "storm_prediction")
    private Double stormPrediction;

    @Column(name = "precipitation_amount")
    private Double precipitationAmount;

    @Column(name = "precipitation_type")
    private String precipitationType;

    @Column(name = "phenomenon_code")
    private Double phenomenonCode;

    @Column(name = "icon_id")
    private String iconId;

    @Column(name = "wind_speed")
    private Double windSpeed;
}
