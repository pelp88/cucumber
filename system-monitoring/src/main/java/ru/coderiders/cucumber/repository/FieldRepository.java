package ru.coderiders.cucumber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.coderiders.cucumber.entity.Field;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью участка
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */
@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    @Query(value = "SELECT * " +
            "FROM public.fields " +
            "WHERE fields.id_plant = :plantId",
            nativeQuery = true)
    List<Field> getFieldsByPlantId(@Param("plantId") Long plantId);

    Optional<Field> findFieldBySensorSerialNumberAndPlantNotNull(UUID serialNumber);
}
