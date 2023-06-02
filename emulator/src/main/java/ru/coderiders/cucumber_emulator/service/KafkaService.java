package ru.coderiders.cucumber_emulator.service;

import ru.coderiders.cucumber_emulator.rest.dto.ReadingDtoForCreating;

public interface KafkaService {
    /**
     * Генерация данных всех датчиков
     */
    void generateAllData();

    /**
     * Принимаем параметры для создания датчиков
     * @param readingDtoForCreating принимаема дто
     */
    void createSensor(ReadingDtoForCreating readingDtoForCreating);

}
