package ru.coderiders.cucumber.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.coderiders.cucumber.rest.api.ReportAPI;
import ru.coderiders.cucumber.rest.dto.UserReportDto;
import ru.coderiders.cucumber.service.ReportService;

import javax.validation.Valid;
import java.time.LocalDate;

@Valid
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearer")
public class ReportController implements ReportAPI {

    private final ReportService reportService;

    @Override
    public UserReportDto getUserReport(Long id, LocalDate firstDate, LocalDate secondDate) {
        return reportService.getUserReport(id, firstDate, secondDate);
    }
}
