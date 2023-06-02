package ru.coderiders.cucumber_emulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coderiders.cucumber_emulator.entity.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
