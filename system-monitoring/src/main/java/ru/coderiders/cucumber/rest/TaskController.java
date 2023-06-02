package ru.coderiders.cucumber.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.coderiders.cucumber.rest.api.TaskApi;
import ru.coderiders.cucumber.rest.dto.TaskRqDto;
import ru.coderiders.cucumber.rest.dto.TaskRsDto;
import ru.coderiders.cucumber.service.TaskService;

import javax.validation.Valid;

/**
 * Контроллер для работы с работами
 *
 * @author Artyom Nikiforov - pelp88@outlook.com
 */

@Valid
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearer")
public class TaskController implements TaskApi {
    private final TaskService taskService;

    @Override
    public ResponseEntity<TaskRsDto> create(TaskRqDto taskRqDto) {
        var createdTask = taskService.create(taskRqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);


    @Override
    public Page<TaskRsDto> findAll(Pageable pageable) {
        return taskService.findAll(pageable);
    }

    @Override
    public TaskRsDto update(Long taskId, TaskRqDto taskRqDto) {
        return taskService.update(taskId, taskRqDto);
    }

    @Override
    public TaskRsDto findById(Long taskId) {
        return taskService.findById(taskId);
    }

    @Override
    public ResponseEntity<Void> delete(Long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.accepted().build();
    }

    @Override
    public TaskRsDto takeTaskByUser(Long taskId, Long userId) {
        return taskService.takeTaskByUser(taskId, userId);
    }

    @Override
    public TaskRsDto closeTask(Long taskId) {
        return taskService.closeTask(taskId);
    }
}
