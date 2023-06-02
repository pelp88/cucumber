package ru.coderiders.cucumber.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.coderiders.cucumber.entity.Task;
import ru.coderiders.cucumber.rest.dto.TaskRqDto;
import ru.coderiders.cucumber.rest.dto.TaskRsDto;

import javax.annotation.PostConstruct;

/**
 * Маппер работ
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */

@Component
@AllArgsConstructor
public class TaskMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.createTypeMap(TaskRqDto.class, Task.class)
                .addMappings(mappings -> {
                    mappings.skip(Task::setField);
                    mappings.skip(Task::setUser);
                });

        modelMapper.createTypeMap(Task.class, TaskRsDto.class);

        modelMapper.createTypeMap(TaskRsDto.class, TaskRqDto.class)
                .addMappings(mappings -> {
                    mappings.map(taskRsDto -> taskRsDto.getField().getId(), TaskRqDto::setField);
                    mappings.map(taskRsDto -> taskRsDto.getUser().getId(), TaskRqDto::setUser);
                });
    }

    public Task toEntity(TaskRqDto taskRqDto) {
        return modelMapper.map(taskRqDto, Task.class);
    }

    public TaskRsDto toRsDto(Task task) {
        return modelMapper.map(task, TaskRsDto.class);
    }

    public TaskRqDto toRqDto(TaskRsDto task) {
        return modelMapper.map(task, TaskRqDto.class);
    }


}
