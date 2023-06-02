package ru.coderiders.cucumber.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.coderiders.cucumber.enums.TaskStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Входящее DTO работы
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRqDto {
    @Schema(description = "Идентификатор пользователя")
    private Long user;
    @NotNull
    @Schema(description = "Идентификатор карточки грядки")
    private Long field;
    @NotNull
    @Schema(description = "Тип задачи")
    private String type;
    @Schema(description = "Время начала выполнения")
    private LocalDateTime startTime;
    @Schema(description = "Время окончания выполнения")
    private LocalDateTime endTime;
    @NotNull
    @Schema(description = "Статус задачи")
    private TaskStatus status;

    public TaskRqDto(Long field, TaskStatus status, String type) {
        this.field = field;
        this.status = status;
        this.type = type;
    }
}
