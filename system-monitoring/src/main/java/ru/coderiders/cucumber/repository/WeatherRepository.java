package ru.coderiders.cucumber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.coderiders.cucumber.entity.Weather;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    @Query("SELECT w FROM Weather w WHERE w.fieldId.id = ?1")
    List<Weather> findByFieldId(Long fieldId);

    Optional<Weather> findWeatherByTime(LocalDateTime time);
}
