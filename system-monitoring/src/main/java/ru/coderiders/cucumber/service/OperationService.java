package ru.coderiders.cucumber.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.coderiders.cucumber.rest.dto.OperationRqDto;
import ru.coderiders.cucumber.rest.dto.OperationRsDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public interface OperationService {

    /**
     * Создание карточки операций
     *
     * @param operationRqDto входное dto операций
     * @return ответное dto операции
     */
    OperationRsDto create(OperationRqDto operationRqDto);

    /**
     * Поиск всех операций
     *
     * @param pageable параметры страницы запроса
     * @return страница найденных операций
     */
    Page<OperationRsDto> findAll(Pageable pageable);

    /**
     * Поиск всех операций, у которых дата меньше или равна текущей
     *
     * @param date параметры страницы запроса
     * @return лист найденных операций
     */
    List<OperationRsDto> getAllOperationsByProcDate(LocalDate date);

    /**
     * Поиск операции по идентификатору
     *
     * @param id идентификатор операции
     * @return ответное dto операции
     */
    OperationRsDto findById(@NotNull Long id);

    /**
     * Обновление операции по идентификатору
     *
     * @param id             идентификатор операции
     * @param operationRqDto входное dto операции
     * @return ответное dto операции
     */
    OperationRsDto update(@NotNull Long id, @NotNull OperationRqDto operationRqDto);

    /**
     * Удаление операции по идентификатору
     *
     * @param id идентификатор операции
     */
    void delete(@NotNull Long id);

    /**
     * Изменение даты срабатывания у операции
     *
     * @param id идентификатор операции
     */
    OperationRsDto changeProcDate(@NotNull Long id, @NotNull LocalDate newProcDate);

}
