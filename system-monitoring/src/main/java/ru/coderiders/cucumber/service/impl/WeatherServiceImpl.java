package ru.coderiders.cucumber.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coderiders.cucumber.mapper.WeatherMapper;
import ru.coderiders.cucumber.repository.FieldRepository;
import ru.coderiders.cucumber.repository.WeatherRepository;
import ru.coderiders.cucumber.rest.dto.WeatherRqDto;
import ru.coderiders.cucumber.rest.dto.WeatherRsDto;
import ru.coderiders.cucumber.rest.exception.NotFoundException;
import ru.coderiders.cucumber.service.WeatherService;
import ru.coderiders.weathergetter.rest.WeatherGetterController;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Сервис для работы с погодой
 *
 * @author Artyom Nikiforov
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private static final String WEATHER_FORECAST_IS_NOT_FOUND_BY_ID_MSG = "Прогноз погоды с id = %d не найден";
    private static final String WEATHER_FORECAST_IS_NOT_FOUND_BY_DATE_MSG = "Прогноз погоды с датой = %s не найден";
    private static final String FIELD_IS_NOT_FOUND_BY_ID_MSG = "Участок с id = %d не найден";

    private final FieldRepository fieldRepository;
    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;
    private final WeatherGetterController weatherGetter;

    @Override
    @Transactional
    public WeatherRsDto create(WeatherRqDto weatherRqDto) {
        var forecast = weatherMapper.toEntity(weatherRqDto);

        Optional.ofNullable(weatherRqDto.getField())
                .flatMap(fieldRepository::findById)
                .ifPresentOrElse(forecast::setFieldId,
                        () -> {
                            throw new NotFoundException(String.format(FIELD_IS_NOT_FOUND_BY_ID_MSG,
                                    weatherRqDto.getField()));
                        });

        return weatherMapper.toDto(weatherRepository.save(forecast));
    }

    @Override
    @Transactional
    public Page<WeatherRsDto> findAll(Pageable pageable) {
        return weatherRepository.findAll(pageable).map(weatherMapper::toDto);
    }


    @Override
    @Transactional
    public void delete(@NotNull Long id) {
        weatherRepository.findById(id)
                .ifPresentOrElse(weatherRepository::delete,
                        () -> {
                            throw new NotFoundException(
                                    String.format(WEATHER_FORECAST_IS_NOT_FOUND_BY_ID_MSG, id));
                        });
    }

    @Override
    public WeatherRsDto findWeatherByTime(LocalDateTime time) {
        return weatherRepository.findWeatherByTime(time).map(weatherMapper::toDto)
                .orElseThrow(() -> {
                    throw new NotFoundException(
                            String.format(WEATHER_FORECAST_IS_NOT_FOUND_BY_DATE_MSG, time));
                });
    }

    @Scheduled(cron = "0 0 0,12 * * *")
    public void updateForecast() {
        log.info("Начато обновление прогноза погоды в " + LocalDateTime.now());

        for (var field : fieldRepository.findAll(Pageable.unpaged())) {
            var location = List.of(field.getLatitude(), field.getLongitude());
            var forecast = weatherGetter.getWeather(location.get(0), location.get(1));

            if (forecast.getStatusCodeValue() == 200) {
                var body = forecast.getBody();
                Objects.requireNonNull(body)
                        .forEach(weatherDto -> create(new WeatherRqDto(
                                field.getId(),
                                weatherDto.getTime(),
                                weatherDto.getAirTemperature(),
                                weatherDto.getHumidity(),
                                weatherDto.getPressure(),
                                weatherDto.getDescription(),
                                weatherDto.getCloudinessPercent(),
                                weatherDto.getStormPrediction(),
                                weatherDto.getPrecipitationAmount(),
                                weatherDto.getPrecipitationType(),
                                weatherDto.getPhenomenonCode(),
                                weatherDto.getIconId(),
                                weatherDto.getWindSpeed()
                        )));
            } else {
                log.info("Ошибка получения погоды для участка! ID = " + field.getId());
            }
        }

        for (var forecast : findAll(Pageable.unpaged())) {
            if (forecast.getTime().isBefore(LocalDateTime.now())) {
                delete(forecast.getId());
            }
        }

        log.info("Закончено обновление прогноза погоды в " + LocalDateTime.now());
    }
}
