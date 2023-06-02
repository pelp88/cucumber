package ru.coderiders.cucumber.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.coderiders.cucumber.rest.dto.PlantRqDto;
import ru.coderiders.cucumber.rest.dto.PlantRsDto;

import javax.validation.constraints.NotNull;

/**
 * Сервис дря работы c растениями
 *
 * @author Gleb Luchinkin
 */
public interface PlantService {

    /**
     * Создание карточки культуры
     *
     * @param plantRqDto входное dto культуры
     * @return ответное dto культуры
     */
    PlantRsDto create(PlantRqDto plantRqDto);

    /**
     * Поиск всех культур
     *
     * @param pageable параметры страницы запроса
     * @return страница найденных культур
     */
    Page<PlantRsDto> findAll(Pageable pageable);

    /**
     * Поиск культуры по идентификатору
     *
     * @param id идентификатор культуры
     * @return ответное dto культуры
     */
    PlantRsDto findById(@NotNull Long id);

    /**
     * Обновление культуры по идентификатору
     *
     * @param id         идентификатор культуры
     * @param plantRqDto входное dto культуры
     * @return ответное dto культуры
     */
    PlantRsDto update(@NotNull Long id, @NotNull PlantRqDto plantRqDto);

    /**
     * Удаление культуры по идентификатору
     *
     * @param id идентификатор культуры
     */
    void delete(@NotNull Long id);
}
