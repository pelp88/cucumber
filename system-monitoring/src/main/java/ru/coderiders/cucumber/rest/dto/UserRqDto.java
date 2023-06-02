package ru.coderiders.cucumber.rest.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Входное dto пользователя
 *
 * @author Anton Belyakov
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRqDto {
    @NotNull
    @Schema(description = "Имя пользователя")
    private String name;
    @NotNull
    @Schema(description = "Роль пользователя")
    private String role;
    @NotNull
    @Schema(description = "Пароль пользователя")
    private String password;
    @NotNull
    @Schema(description = "Email пользователя")
    private String email;
}
