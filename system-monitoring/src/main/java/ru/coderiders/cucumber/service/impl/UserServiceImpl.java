package ru.coderiders.cucumber.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.coderiders.cucumber.entity.User;
import ru.coderiders.cucumber.mapper.UserMapper;
import ru.coderiders.cucumber.repository.UserRepository;
import ru.coderiders.cucumber.rest.dto.UserRqDto;
import ru.coderiders.cucumber.rest.dto.UserRsDto;
import ru.coderiders.cucumber.rest.exception.NotFoundException;
import ru.coderiders.cucumber.rest.exception.UserExistsException;
import ru.coderiders.cucumber.service.UserService;
import ru.coderiders.cucumber.specification.UserSpecification;
import ru.coderiders.cucumber.util.BeanUtilsHelper;

import javax.validation.constraints.NotNull;

/**
 * Сервис для работы с пользователями
 *
 * @author Anton Belyakov
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_IS_NOT_FOUND_BY_ID_MSG = "Пользователь с id = %d не найден";
    private static final String[] IGNORED_ON_COPY_FIELDS = {"id"};

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserRsDto create(UserRqDto userRqDto) throws UserExistsException {
        if (!userRepository.findAllWithNameLike(userRqDto.getName()).isEmpty()) {
            throw new UserExistsException("Пользователь " + userRqDto.getName() + " существует!");
        }
        userRqDto.setPassword(passwordEncoder.encode(userRqDto.getPassword()));
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userRqDto)));
    }

    @Override
    @Transactional
    public Page<UserRsDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    @Transactional
    public UserRsDto findById(@NotNull Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(USER_IS_NOT_FOUND_BY_ID_MSG, id)));
    }

    @Override
    @Transactional
    public void delete(@NotNull Long id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserRsDto update(Long id, UserRqDto userRqDto) {
        return userRepository.findById(id)
                .map(user -> {
                    var newUser = userMapper.toEntity(userRqDto);

                    BeanUtils.copyProperties(newUser, user,
                            BeanUtilsHelper.getNullPropertyNames(newUser, IGNORED_ON_COPY_FIELDS));

                    return user;
                })
                .map(userMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(USER_IS_NOT_FOUND_BY_ID_MSG, id)));
    }

    @Override
    public Page<UserRsDto> findUsersFilteredByRole(String role, Pageable pageable) {
        Specification<User> specification = Specification.where(UserSpecification.roleLike(role));
        return userRepository.findAll(specification, pageable).map(userMapper::toDto);
    }

    @Override
    public Page<UserRsDto> findUsersWithIdAboveNumber(Long number, Pageable pageable) {
        Specification<User> specification = Specification.where(UserSpecification.idAbove(number));
        return userRepository.findAll(specification, pageable).map(userMapper::toDto);
    }

    @Override
    public Page<UserRsDto> findUsersWithIdBelowNumber(Long number, Pageable pageable) {
        Specification<User> specification = Specification.where(UserSpecification.idBelow(number));
        return userRepository.findAll(specification, pageable).map(userMapper::toDto);
    }

}
