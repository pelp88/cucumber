package ru.coderiders.cucumber.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.coderiders.cucumber.enums.TaskStatus;

import java.time.LocalDateTime;

/**
 * Исходящее DTO работы
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRsDto {
    @Schema(description = "Идентификатор задачи")
    private Long id;
    @Schema(description = "Ответное dto пользователя")
    private UserRsDto user;
    @Schema(description = "Ответное dto карточки грядки")
    private FieldRsDto field;
    @Schema(description = "Тип задачи")
    private String type;
    @Schema(description = "Время начала выполнения")
    private LocalDateTime startTime;
    @Schema(description = "Время окончания выполнения")
    private LocalDateTime endTime;
    @Schema(description = "Статус задачи")
    private TaskStatus status;
}
