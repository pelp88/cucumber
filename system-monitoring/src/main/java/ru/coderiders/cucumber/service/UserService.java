package ru.coderiders.cucumber.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.coderiders.cucumber.rest.dto.UserRqDto;
import ru.coderiders.cucumber.rest.dto.UserRsDto;

/**
 * Сервис для работы с пользователями
 *
 * @author Anton Belyakov
 */
public interface UserService {

    UserRsDto create(UserRqDto userRqDto);

    Page<UserRsDto> findAll(Pageable pageable);

    UserRsDto findById(Long id);

    void delete(Long id);

    UserRsDto update(Long id, UserRqDto userRqDto);

    Page<UserRsDto> findUsersFilteredByRole(String role, Pageable pageable);

    Page<UserRsDto> findUsersWithIdAboveNumber(Long number, Pageable pageable);

    Page<UserRsDto> findUsersWithIdBelowNumber(Long number, Pageable pageable);

}
