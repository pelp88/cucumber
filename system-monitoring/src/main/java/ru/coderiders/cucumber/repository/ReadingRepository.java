package ru.coderiders.cucumber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coderiders.cucumber.entity.Reading;

import java.util.Optional;
import java.util.UUID;

public interface ReadingRepository extends JpaRepository<Reading, Long> {
    Optional<Reading> findFirstBySerialNumberOrderByTimeDesc(UUID serialNumber);
}
