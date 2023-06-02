package ru.coderiders.cucumber.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;
import ru.coderiders.cucumber.rest.api.UserApi;
import ru.coderiders.cucumber.rest.dto.UserRqDto;
import ru.coderiders.cucumber.rest.dto.UserRsDto;
import ru.coderiders.cucumber.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

/**
 * Контроллер для работы с пользователями
 *
 * @author Anton Belyakov
 */

@Valid
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearer")
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    @Secured("ADMIN")
    public ResponseEntity<UserRsDto> create(UserRqDto userRqDto) {
        var createdUser = userService.create(userRqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Override
    @Secured("ADMIN")
    public Page<UserRsDto> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @Override
    @Secured("ADMIN")
    public UserRsDto update(Long userId, UserRqDto userRqDto) {
        return userService.update(userId, userRqDto);
    }

    @Override
    @Secured("ADMIN")
    public UserRsDto findById(Long userId) {
        return userService.findById(userId);
    }

    @Override
    @Secured("ADMIN")
    public ResponseEntity<Void> delete(Long userId) {
        userService.delete(userId);
        return ResponseEntity.accepted().build();
    }

    @Override
    @Secured("ADMIN")
    public Page<UserRsDto> findAllByRole(String role, Pageable pageable) {
        return userService.findUsersFilteredByRole(role, pageable);
    }

    @Override
    @Secured("ADMIN")
    public Page<UserRsDto> findAllThanAboveNumber(Long number, Pageable pageable) {
        return userService.findUsersWithIdAboveNumber(number, pageable);
    }

    @Override
    @Secured("ADMIN")
    public Page<UserRsDto> findAllThanBelowNumber(Long number, Pageable pageable) {
        return userService.findUsersWithIdBelowNumber(number, pageable);
    }

}
