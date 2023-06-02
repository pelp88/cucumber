package ru.coderiders.cucumber.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.coderiders.cucumber.entity.Field;
import ru.coderiders.cucumber.entity.Task;
import ru.coderiders.cucumber.enums.TaskStatus;
import ru.coderiders.cucumber.mapper.TaskMapper;
import ru.coderiders.cucumber.mapper.UserMapper;
import ru.coderiders.cucumber.repository.FieldRepository;
import ru.coderiders.cucumber.repository.TaskRepository;
import ru.coderiders.cucumber.repository.UserRepository;
import ru.coderiders.cucumber.rest.dto.TaskRqDto;
import ru.coderiders.cucumber.rest.dto.TaskRsDto;
import ru.coderiders.cucumber.rest.dto.UserRsDto;
import ru.coderiders.cucumber.rest.exception.NotFoundException;
import ru.coderiders.cucumber.service.TaskService;
import ru.coderiders.cucumber.util.BeanUtilsHelper;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для работы с тасками
 *
 * @author Artyom Nikiforov - pelp88@outlook.com
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private static final String TASK_IS_NOT_FOUND_BY_ID_MSG = "Работа с id = %d не найдена";
    private static final String[] IGNORED_ON_COPY_FIELDS = {"id"};
    private static final String FIELD_IS_NOT_FOUND_BY_ID_MSG = "Участок с id = %d не найден";
    private static final String USER_IS_NOT_FOUND_BY_ID_MSG = "Пользователь с id = %d не найден";

    private final TaskRepository taskRepository;
    private final FieldRepository fieldRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;

    private final EmailService emailService;

    @Override
    @Transactional
    @PreAuthorize("principal.authorities[0].authority == 'ADMIN' or #taskRqDto.user == principal.user.id")
    public TaskRsDto create(TaskRqDto taskRqDto) {
        if (taskRepository.getAllByField_IdAndStatusNot(taskRqDto.getField(), TaskStatus.CLOSED).stream()
                .map(Task::getType)
                .collect(Collectors.toList())
                .stream().anyMatch(s -> s.equals(taskRqDto.getType()) || s.equals("Выкорчевать растение"))) {
            return new TaskRsDto();
        }

        var task = taskMapper.toEntity(taskRqDto);
        Optional.ofNullable(taskRqDto.getField()).flatMap(fieldRepository::findById).ifPresentOrElse(task::setField,
                () -> {
                    throw new NotFoundException(String.format(FIELD_IS_NOT_FOUND_BY_ID_MSG, taskRqDto.getField()));
                });
        Optional.ofNullable(taskRqDto.getUser()).flatMap(userRepository::findById).ifPresent(task::setUser);
        var created = taskRepository.save(task);

        if (taskRqDto.getUser() != null) {
            try {
                emailService.sendMessage(created.getUser().getEmail(),
                        "Создана задача: " + created.getId(),
                        "Была создана задача: №" + created.getId()
                                + ", тип: " + created.getType() + ". Участок: №" + created.getField().getId());
                log.info("Отправлено уведомление по задаче для пользователя № " + created.getUser());
            } catch (Exception e) {
                log.error("Ошибка отправки уведомления! " + e.getMessage());
            }
        }

        return taskMapper.toRsDto(created);
    }

    @Override
    @Transactional
    public Page<TaskRsDto> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(taskMapper::toRsDto);
    }

    @Override
    public List<TaskRsDto> findAllByFieldId(Long id) {
        return taskRepository.getAllByField_Id(id).stream()
                .map(taskMapper::toRsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskRsDto findById(@NotNull Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toRsDto)
                .orElseThrow(() -> new NotFoundException(String.format(TASK_IS_NOT_FOUND_BY_ID_MSG, id)));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PreAuthorize("principal.authorities[0].authority == 'ADMIN' or #taskRqDto.user == principal.user.id")
    public TaskRsDto update(Long id, TaskRqDto taskRqDto) {
        return taskRepository.findById(id)
                .map(task -> {
                    var newTask = taskMapper.toEntity(taskRqDto);

                    Optional.ofNullable(taskRqDto.getField())
                            .flatMap(fieldRepository::findById)
                            .ifPresentOrElse(task::setField,
                                    () -> {
                                        throw new NotFoundException(String.format(FIELD_IS_NOT_FOUND_BY_ID_MSG,
                                                taskRqDto.getField()));
                                    });

                    Optional.ofNullable(taskRqDto.getUser())
                            .flatMap(userRepository::findById)
                            .ifPresent(task::setUser);

                    BeanUtils.copyProperties(newTask, task,
                            BeanUtilsHelper.getNullPropertyNames(newTask, IGNORED_ON_COPY_FIELDS));

                    if (task.getUser() != null) {
                        try {
                            emailService.sendMessage(task.getUser().getEmail(),
                                    "Обновлена задача: " + task.getId(),
                                    "Была обновлена задача: №" + task.getId()
                                            + ", тип: " + task.getType() + ". Участок: №" + task.getField().getId()
                                            + ". Статус: " + task.getStatus().message);
                            log.info("Отправлено уведомление по задаче для пользователя № " + task.getUser());
                        } catch (Exception e) {
                            log.error("Ошибка отправки уведомления! " + e.getMessage());
                        }
                    }

                    return task;
                })
                .map(taskMapper::toRsDto)
                .orElseThrow(() -> new NotFoundException(String.format(TASK_IS_NOT_FOUND_BY_ID_MSG, id)));
    }

    @Override
    @Transactional
    public TaskRsDto takeTaskByUser(Long taskId, Long userId) {
        TaskRsDto foundedTask = findById(taskId);
        UserRsDto foundedUser = userRepository.findById(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(USER_IS_NOT_FOUND_BY_ID_MSG, userId)));
        foundedTask.setUser(foundedUser);
        foundedTask.setStartTime(LocalDateTime.now());
        foundedTask.setStatus(TaskStatus.IN_PROGRESS);
        return update(taskId, taskMapper.toRqDto(foundedTask));
    }

    @Override
    @Transactional
    public TaskRsDto closeTask(Long taskId) {
        TaskRsDto foundedTask = findById(taskId);
        foundedTask.setStatus(TaskStatus.CLOSED);
        foundedTask.setEndTime(LocalDateTime.now());
        if (foundedTask.getField().getPlantHealth() < 100 && foundedTask.getField().getPlant() != null) {
            Field updatedField = fieldRepository.findById(foundedTask.getField().getId())
                    .orElseThrow(() -> new NotFoundException(String.format(FIELD_IS_NOT_FOUND_BY_ID_MSG,
                            foundedTask.getField().getId())));
            updatedField.setPlantHealth(updatedField.getPlantHealth() + 10);
            BeanUtils.copyProperties(updatedField, foundedTask.getField().getPlant(),
                    BeanUtilsHelper.getNullPropertyNames(updatedField, IGNORED_ON_COPY_FIELDS));
        }
        return update(taskId, taskMapper.toRqDto(foundedTask));
    }

    @Override
    @Transactional
    public void delete(@NotNull Long id) {
        taskRepository.findById(id).ifPresent(taskRepository::delete);
    }
}
