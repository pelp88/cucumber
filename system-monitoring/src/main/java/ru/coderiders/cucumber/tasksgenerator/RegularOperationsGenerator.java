package ru.coderiders.cucumber.tasksgenerator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.coderiders.cucumber.enums.TaskStatus;
import ru.coderiders.cucumber.rest.dto.TaskRqDto;
import ru.coderiders.cucumber.service.FieldService;
import ru.coderiders.cucumber.service.OperationService;
import ru.coderiders.cucumber.service.TaskService;

import java.time.LocalDate;

@AllArgsConstructor
@Component
@Slf4j
public class RegularOperationsGenerator {

    private final OperationService operationService;
    private final FieldService fieldService;
    private final TaskService taskService;

    @Scheduled(fixedDelayString = "${delayString}")
    public void createTaskWithGoodOperationDate() {
        LocalDate currentDate = LocalDate.now();
        log.info("Начало проверки регулярных операций для: " + currentDate);
        operationService.getAllOperationsByProcDate(currentDate)
                .forEach(operation -> fieldService.getFieldsByPlantId(operation.getPlantId().getId())
                        .forEach((field -> {
                            TaskRqDto taskForCreating = new TaskRqDto();
                            if (operation.getProcDate().isBefore(currentDate)) {
                                operation.setProcDate(currentDate);
                            }
                            taskForCreating.setType(operation.getOperationName().getName());
                            taskForCreating.setStatus(TaskStatus.OPEN);
                            taskForCreating.setField(field.getId());
                            taskService.create(taskForCreating);
                            operation.setProcDate(operation.getProcDate().plusDays(operation.getInterval()));
                            operationService.changeProcDate(operation.getId(), operation.getProcDate());
                            log.info("Создана задача:" + taskForCreating);
                        })));
    }
}
