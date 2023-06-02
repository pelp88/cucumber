package ru.coderiders.cucumber.service.visitor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.coderiders.cucumber.enums.TaskStatus;
import ru.coderiders.cucumber.rest.dto.FieldRsDto;
import ru.coderiders.cucumber.rest.dto.ReadingDto;
import ru.coderiders.cucumber.rest.dto.TaskRqDto;
import ru.coderiders.cucumber.service.TaskService;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class RipeningDateReadingVisitor implements ReadingVisitor {

    private final TaskService taskService;

    @Override
    public void visit(ReadingDto reading, FieldRsDto field) {
        var plant = Objects.requireNonNull(field.getPlant());
        if (reading.getTime().toLocalDate().isAfter(plant.getRipeningDate().minusDays(1))) {
            taskService.create(new TaskRqDto(field.getId(), TaskStatus.OPEN, "Собрать урожай"));
        }
    }
}
