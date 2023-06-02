package ru.coderiders.cucumber.service;

import ru.coderiders.cucumber.rest.dto.ReadingDto;

public interface ReadingsService {
    void consumeReadings(ReadingDto readingDto);
}
