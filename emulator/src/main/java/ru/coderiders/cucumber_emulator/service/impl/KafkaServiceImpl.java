package ru.coderiders.cucumber_emulator.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.coderiders.cucumber_emulator.entity.Sensor;
import ru.coderiders.cucumber_emulator.rest.dto.ReadingDto;
import ru.coderiders.cucumber_emulator.rest.dto.ReadingDtoForCreating;
import ru.coderiders.cucumber_emulator.rest.exception.NotFoundException;
import ru.coderiders.cucumber_emulator.service.KafkaService;
import ru.coderiders.cucumber_emulator.service.SensorService;
import ru.coderiders.weathergetter.rest.WeatherGetterController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<Long, ReadingDto> kafkaTemplate;
    private final SensorService sensorService;
    private final WeatherGetterController weatherGetterController;
    private final Random rng = new Random();

    @Scheduled(fixedDelay = 10000)
    public void generateAllData() {
        var sensors = sensorService.findAll(Pageable.unpaged());

        sensors.forEach(sensor -> {

            var readingDto = new ReadingDto();
            var geolocation = List.of(sensor.getLatitude(), sensor.getLongitude());
            var weather = Objects.requireNonNull(weatherGetterController
                            .getWeather(geolocation.get(0), geolocation.get(1))
                            .getBody())
                    .stream().reduce((first, second) -> second)
                    .orElseThrow(() -> new NotFoundException("Не удалось получить прогноз погоды для датчика "
                            + sensor.getId()));

            readingDto.setAirTemperature(getRandomDouble(weather.getAirTemperature(),
                    sensor.getTempOffset()));
            readingDto.setHumidity(getRandomInt(weather.getHumidity(),
                    sensor.getHumidityOffset()));
            readingDto.setPressure(getRandomInt(weather.getPressure(),
                    sensor.getPressureOffset()));
            readingDto.setPrecipitationAmount(getRandomDouble(weather.getPrecipitationAmount(),
                    sensor.getPrecipitationAmountOffset()));
            readingDto.setWindSpeed(getRandomDouble(weather.getWindSpeed(),
                    sensor.getWindSpeedOffset()));
            readingDto.setManureLevel(getRandomManureOrChemical(sensor.getManureLevel(),
                    sensor.getManureOffset()));
            readingDto.setChemicalLevel(getRandomManureOrChemical(sensor.getChemicalLevel(),
                    sensor.getChemicalOffset()));
            readingDto.setSerialNumber(sensor.getSerialNumber());
            readingDto.setTime(LocalDateTime.now());

            kafkaTemplate.send("readings.created", readingDto);
        });
    }

    @Override
    @KafkaListener(topics = "sensor.creating", groupId = "myGroup", containerFactory = "singleFactory")
    public void createSensor(ReadingDtoForCreating readingDtoForCreating) {
        log.info("consume => " + readingDtoForCreating.toString());
        Sensor sensor = new Sensor();
        sensor.setLatitude(readingDtoForCreating.getLatitude());
        sensor.setLongitude(readingDtoForCreating.getLongitude());
        sensor.setSerialNumber(readingDtoForCreating.getSensorSerialNumber());
        sensor.setTempOffset(generateRandomOffset());
        sensor.setHumidityOffset(generateRandomOffset().intValue());
        sensor.setPressureOffset(generateRandomOffset().intValue());
        sensor.setPrecipitationAmountOffset(generateRandomOffset());
        sensor.setWindSpeedOffset(generateRandomOffset());
        sensor.setChemicalOffset(generateRandomOffset());
        sensor.setManureOffset(generateRandomOffset());
        sensorService.createWithValuesFromKafka(sensor);
    }

    private Double generateRandomOffset() {
        return Math.random() * (40 - 20 + 1) + 20;
    }

    private Double getRandomDouble(Double reading, Double offset) {
        if (reading != null) return Math.abs(rng.doubles(reading - offset,
                        reading + offset)
                .findFirst().orElseThrow());
        else return null;
    }

    private Integer getRandomInt(Integer reading, Integer offset) {
        if (reading != null) return Math.abs(rng.ints(reading - offset,
                        reading + offset)
                .findFirst().orElseThrow());
        else return null;
    }

    private Double getRandomManureOrChemical(Double counter, Double offset) {
        if (counter <= 95) return Math.abs(rng.doubles(counter,
                        counter + offset)
                .findFirst().orElseThrow());
        else return 100.0;
    }
}
