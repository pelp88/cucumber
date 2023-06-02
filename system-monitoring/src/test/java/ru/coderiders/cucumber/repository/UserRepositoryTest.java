package ru.coderiders.cucumber.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.coderiders.cucumber.entity.User;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        Stream.generate(() -> new User(null, "name", "user", "pass", "test@test.com"))
                .limit(3)
                .forEach(user -> userRepository.save(user));
        Stream.generate(() -> new User(null, "amogus", "not a user", "pwd", "test1@test.com"))
                .limit(3)
                .forEach(user -> userRepository.save(user));
    }

    @AfterEach
    void clearRepository() {
        userRepository.deleteAll();
    }

    @Test
    void findAllWithRoleLike() {
        List<User> usersWithSpecialRole = userRepository.findAllWithRoleLike("user");
        for (User user : usersWithSpecialRole) {
            assertEquals("user", user.getRole());
        }
        assertTrue(usersWithSpecialRole.size() > 0);
    }

    @Test
    void findAllWithNameLike() {
        List<User> usersWithSpecialName = userRepository.findAllWithNameLike("amogus");
        for (User user : usersWithSpecialName) {
            assertEquals("amogus", user.getName());
        }
        assertTrue(usersWithSpecialName.size() > 0);
    }

    @Test
    void findUsersWithIdAboveNumber() {
        Long number = userRepository.findAll().get(2).getId();
        List<User> usersWithIdAboveNumber = userRepository.findUsersWithIdAboveNumber(number);
        for (User user : usersWithIdAboveNumber) {
            assertTrue(user.getId() >= number);
        }
        assertTrue(usersWithIdAboveNumber.size() > 0);
    }

    @Test
    void findUsersWithIdBelowNumber() {
        Long number = userRepository.findAll().get(2).getId();
        List<User> usersWithIdBelowNumber = userRepository.findUsersWithIdBelowNumber(number);
        for (User user : usersWithIdBelowNumber) {
            assertTrue(user.getId() <= number);
        }
        assertTrue(usersWithIdBelowNumber.size() > 0);
    }
}