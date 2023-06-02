package ru.coderiders.cucumber.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.coderiders.cucumber.entity.Task;
import ru.coderiders.cucumber.entity.User;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReportDto {
    @Schema(description = "пользователь")
    private User user;
    @Schema(description = "закрытые задачи пользователя")
    private List<Task> userClosedTasks;
}
