package ru.coderiders.cucumber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.coderiders.cucumber.entity.Plant;
import ru.coderiders.cucumber.enums.SoilType;

import java.util.List;


/**
 * Репозиторий для работы с сущностью растения (Plant)
 *
 * @author Gleb Luchinkin
 */
@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findPlantsBySoilTypeContaining(SoilType soilType);
}
