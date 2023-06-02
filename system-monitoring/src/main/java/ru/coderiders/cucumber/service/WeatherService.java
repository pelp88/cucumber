package ru.coderiders.cucumber.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.coderiders.cucumber.rest.dto.WeatherRqDto;
import ru.coderiders.cucumber.rest.dto.WeatherRsDto;

import java.time.LocalDateTime;

/**
 * Сервис для работы с погодой
 *
 * @author Artyom Nikiforov - pelp88@outlook.com
 */
public interface WeatherService {
    /**
     * Создание прогноза погоды
     *
     * @param weatherRqDto входящее DTO участка
     * @return исходящее DTO участка
     */
    WeatherRsDto create(WeatherRqDto weatherRqDto);

    /**
     * Поиск всех прогнозов
     *
     * @param pageable параметры страницы запроса
     * @return страница найденных культур
     */
    Page<WeatherRsDto> findAll(Pageable pageable);


    /**
     * Удалить прогноз по ID
     *
     * @param id ID работы
     */
    void delete(Long id);

    /**
     * Получение прогноза по времени
     *
     * @param time время для получения прогноза
     */
    WeatherRsDto findWeatherByTime(LocalDateTime time);
}

