package ru.coderiders.cucumber_emulator.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.coderiders.cucumber_emulator.entity.Sensor;
import ru.coderiders.cucumber_emulator.rest.dto.SensorRqDto;
import ru.coderiders.cucumber_emulator.rest.dto.SensorRsDto;

/**
 * Сервис для работы с датчиками
 *
 * @author Artyom Nikiforov - pelp88@outlook.com
 */
public interface SensorService {
    /**
     * Создание датчика
     *
     * @param sensorRqDto входящее DTO датчика
     * @return исходящее DTO датчика
     */
    SensorRsDto create(SensorRqDto sensorRqDto);


    void createWithValuesFromKafka(Sensor sensor);

    /**
     * Поиск всех датчиков
     *
     * @param pageable параметры страницы запроса
     * @return страница найденных датчиков
     */
    Page<SensorRsDto> findAll(Pageable pageable);

    /**
     * Поиск датчиков по ID
     *
     * @param id ID датчика
     * @return исходящее DTO датчика
     */
    SensorRsDto findById(Long id);

    /**
     * Удалить датчика по ID
     *
     * @param id ID датчика
     */
    void delete(Long id);

    /**
     * Обновить датчик
     *
     * @param sensorId    ID датчика
     * @param sensorRqDto входящий DTO датчика
     * @return исходящий DTO датчика
     */
    SensorRsDto update(Long sensorId, SensorRqDto sensorRqDto);
}
