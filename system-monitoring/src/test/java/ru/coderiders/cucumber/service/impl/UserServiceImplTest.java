package ru.coderiders.cucumber.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.coderiders.cucumber.entity.User;
import ru.coderiders.cucumber.mapper.UserMapper;
import ru.coderiders.cucumber.repository.UserRepository;
import ru.coderiders.cucumber.rest.dto.UserRqDto;
import ru.coderiders.cucumber.rest.dto.UserRsDto;
import ru.coderiders.cucumber.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    @Test
    void create() {
        UserRsDto userRsDto = userService.create(new UserRqDto("name", "role", "pass", "test@test.com"));
        assertNotNull(userRsDto);
        assertNotNull(userRsDto.getId());
        assertEquals("name", userRsDto.getName());
        assertEquals("role", userRsDto.getRole());
        userRepository.delete(new User(userRsDto.getId(), userRsDto.getName(),
                userRsDto.getRole(), userRsDto.getPassword(), userRsDto.getEmail()));
    }

    @Test
    void findAll() {
        Page<UserRsDto> users = userService.findAll(Pageable.unpaged());
        List<UserRsDto> expectedUsers = userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        assertEquals(expectedUsers, users.toList());
    }

    @Test
    void findById() {
        User saved = userRepository.save(new User(2L, "DummyUser1", "dumb", "pass", "test@test.com"));
        UserRsDto user = userService.findById(saved.getId());
        assertEquals(saved.getId(), user.getId());
        assertEquals(saved.getName(), user.getName());
        assertEquals(saved.getRole(), user.getRole());
        userRepository.delete(saved);
    }

    @Test
    void delete() {
        User saved = userRepository.save(new User(2L, "DummyUser2", "dumb", "pass", "test@test.com"));
        userService.delete(saved.getId());
        assertEquals(Optional.empty(), userRepository.findById(saved.getId()));
        userRepository.delete(saved);
    }

    @Test
    void update() {
        User saved = userRepository.save(new User(2L, "DummyUser3", "dumb", "pass", "test@test.com"));
        UserRqDto dataForUpdate = new UserRqDto("WiseUser4", "wise", "notpass", "test1@test.com");
        UserRsDto updatedUser = userService.update(saved.getId(), dataForUpdate);

        assertEquals(saved.getId(), updatedUser.getId());
        assertEquals("WiseUser4", updatedUser.getName());
        assertEquals("wise", updatedUser.getRole());
        userRepository.delete(saved);
    }

    @Test
    void findUsersFilteredByRole() {
        User saved = userRepository.save(new User(2L, "DummyUser4", "dumb", "pass", "test@test.com"));
        List<UserRsDto> filteredUsers = userService.findUsersFilteredByRole("dumb", Pageable.unpaged()).toList();

        assertEquals(1, filteredUsers.size());
        assertEquals(saved.getRole(), filteredUsers.get(0).getRole());
        userRepository.delete(saved);
    }

    @Test
    void findUsersWithIdAboveNumber() {
        User saved = userRepository.save(new User(2L, "DummyUser5", "dumb", "pass", "test@test.com"));
        Long numberForSort = saved.getId() - 1;
        userService.findUsersWithIdAboveNumber(
                        numberForSort, Pageable.unpaged())
                .toList()
                .forEach(userRsDto -> assertTrue(userRsDto.getId() > numberForSort));
        userRepository.delete(saved);
    }

    @Test
    void findUsersWithIdBelowNumber() {
        User saved = userRepository.save(new User(2L, "DummyUser6", "dumb", "pass", "test@test.com"));
        Long numberForSort = saved.getId() + 1;
        userService.findUsersWithIdBelowNumber(
                        numberForSort, Pageable.unpaged())
                .toList()
                .forEach(userRsDto -> assertTrue(userRsDto.getId() < numberForSort));
        userRepository.delete(saved);
    }
}