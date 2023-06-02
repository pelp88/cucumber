package ru.coderiders.cucumber.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.coderiders.cucumber.rest.dto.TaskRqDto;
import ru.coderiders.cucumber.rest.dto.TaskRsDto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Сервис для работы с тасками
 *
 * @author Artyom Nikiforov - pelp88@outlook.com
 */
public interface TaskService {
    /**
     * Создание задачи
     *
     * @param taskRqDto входящее DTO участка
     * @return исходящее DTO участка
     */
    TaskRsDto create(TaskRqDto taskRqDto);

    /**
     * Поиск всех работ
     *
     * @param pageable параметры страницы запроса
     * @return страница найденных задач
     */
    Page<TaskRsDto> findAll(Pageable pageable);

    /**
     * Поиск всех задач на заданном поле
     *
     * @param id ID поля
     * @return лист найденных задач
     */
    List<TaskRsDto> findAllByFieldId(@NotNull Long id);

    /**
     * Поиск работы по ID
     *
     * @param id ID работы
     * @return исходящее DTO работы
     */
    TaskRsDto findById(@NotNull Long id);

    /**
     * Удалить работу по ID
     *
     * @param id ID работы
     */
    void delete(@NotNull Long id);

    /**
     * Обновить работу
     *
     * @param taskId    ID работы
     * @param taskRqDto входящий DTO работы
     * @return исходящий DTO работы
     */
    TaskRsDto update(@NotNull Long taskId, @NotNull TaskRqDto taskRqDto);

    /**
     * Взять задачу на выполнение
     *
     * @param taskId    ID задачи
     * @param userId    ID рабоника, берущего задачу
     * @return исходящий DTO работы
     */
    TaskRsDto takeTaskByUser(@NotNull Long taskId, @NotNull Long userId);

    /**
     * Закрыть задачу
     *
     * @param taskId    ID задачи
     * @return исходящий DTO работы
     */
    TaskRsDto closeTask(@NotNull Long taskId);
}
