package ru.coderiders.cucumber.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.coderiders.cucumber.rest.api.WeatherApi;
import ru.coderiders.cucumber.rest.dto.WeatherRsDto;
import ru.coderiders.cucumber.service.WeatherService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Valid
@RestController
@RequestMapping("/weather")
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearer")
public class WeatherController implements WeatherApi {

    private final WeatherService weatherService;

    @Override
    public WeatherRsDto findByTime(LocalDateTime dateTime) {
        return weatherService.findWeatherByTime(dateTime);
    }
}
