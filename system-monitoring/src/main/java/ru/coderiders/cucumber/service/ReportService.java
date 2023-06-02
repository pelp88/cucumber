package ru.coderiders.cucumber.service;

import ru.coderiders.cucumber.rest.dto.UserReportDto;

import java.time.LocalDate;

public interface ReportService {
    UserReportDto getUserReport(Long id, LocalDate firstDate, LocalDate secondDate);
}
