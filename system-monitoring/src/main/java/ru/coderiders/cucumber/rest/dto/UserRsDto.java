package ru.coderiders.cucumber.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Ответное dto пользователя
 *
 * @author Anton Belyakov
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRsDto {
    @Schema(description = "Идентификатор пользователя")
    private Long id;
    @Schema(description = "Имя пользователя")
    private String name;
    @Schema(description = "Роль пользователя")
    private String role;
    @Schema(description = "Пароль пользователя")
    private String password;
    @Schema(description = "Email пользователя")
    private String email;
}
