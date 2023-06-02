package ru.coderiders.cucumber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.coderiders.cucumber.entity.Task;
import ru.coderiders.cucumber.enums.TaskStatus;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью работ
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> getAllByField_IdAndStatusNot(Long id, TaskStatus taskStatus);

    List<Task> getAllByField_Id(Long id);

    List<Task> findByStatus(TaskStatus status);

    Optional<Task> findTaskByType(String type);
}
