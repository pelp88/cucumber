package ru.coderiders.cucumber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.coderiders.cucumber.entity.Operation;

import java.time.LocalDate;
import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query(value = "SELECT id, operation_name, work_time, id_plant, \"interval\" " +
            "FROM public.operations " +
            "WHERE operations.work_time <= :currentDate",
            nativeQuery = true)
    List<Operation> getAllOperationsByProcDate(@Param("currentDate") LocalDate currentDate);
}
