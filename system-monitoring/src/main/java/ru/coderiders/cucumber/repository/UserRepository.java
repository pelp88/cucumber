package ru.coderiders.cucumber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.coderiders.cucumber.entity.User;

import java.util.List;

/**
 * Репозиторий для работы с сущностью пользователя (User)
 *
 * @author Anton Belyakov
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query(value = "SELECT id, name, role, password, email FROM users " +
            "WHERE users.role LIKE :inputRole",
            nativeQuery = true)
    List<User> findAllWithRoleLike(@Param("inputRole") String inputRole);

    @Query(value = "SELECT id, name, role, password, email FROM users " +
            "WHERE users.name LIKE :inputName",
            nativeQuery = true)
    List<User> findAllWithNameLike(@Param("inputName") String inputName);

    @Query(value = "SELECT id, name, role, password, email FROM users " +
            "WHERE users.id >= :inputId",
            nativeQuery = true)
    List<User> findUsersWithIdAboveNumber(@Param("inputId") Long inputId);

    @Query(value = "SELECT id, name, role, password, email FROM users " +
            "WHERE users.id <= :inputId",
            nativeQuery = true)
    List<User> findUsersWithIdBelowNumber(@Param("inputId") Long inputId);
}
