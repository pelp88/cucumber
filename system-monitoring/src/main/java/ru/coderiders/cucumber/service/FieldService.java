package ru.coderiders.cucumber.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.coderiders.cucumber.rest.dto.FieldRqDto;
import ru.coderiders.cucumber.rest.dto.FieldRsDto;
import ru.coderiders.cucumber.rest.dto.PlantRsDto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис работы с участком
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */

public interface FieldService {

    /**
     * Создание участка
     *
     * @param fieldRqDto входящее DTO участка
     * @return исходящее DTO участка
     */
    FieldRsDto create(@NotNull FieldRqDto fieldRqDto);

    /**
     * Поиск всех участков
     *
     * @param pageable параметры страницы запроса
     * @return страница найденных участков
     */
    Page<FieldRsDto> findAll(Pageable pageable);

    /**
     * Поиск участок по ID
     *
     * @param id ID участка
     * @return исходящее DTO участка
     */
    FieldRsDto findById(@NotNull Long id);

    /**
     * Поиск участок по ID
     *
     * @param plantId ID растения
     * @return исходящее DTO участка
     */
    List<FieldRsDto> getFieldsByPlantId(Long plantId);

    /**
     * Обновление участка по ID
     *
     * @param id         ID участка
     * @param fieldRqDto входящее DTO участка
     * @return исходящее DTO участка
     */
    FieldRsDto update(@NotNull Long id, @NotNull FieldRqDto fieldRqDto);

    /**
     * Удаление участка
     *
     * @param id ID участка
     */
    void delete(@NotNull Long id);

    /**
     * Посадка расстения на участок
     *
     * @param idPlant ID расстения
     * @param idField ID участка
     */
    FieldRsDto putPlantOnField(@NotNull Long idPlant, @NotNull Long idField);

    /**
     * Выкапывание растения с участка
     *
     * @param idField ID участка
     */
    FieldRsDto digOutPlantOnField(@NotNull Long idField);

    /**
     * Поиск всех засаженных участков
     */
    Optional<FieldRsDto> findFieldByUuidAndPlantNotNull(UUID uuid);

    /**
     * Поиск растений возможных для посадки на поле
     *
     * @param idField ID участка
     */
    List<PlantRsDto> getPlantsAllowedToPlaceOnField(Long idField);
}
