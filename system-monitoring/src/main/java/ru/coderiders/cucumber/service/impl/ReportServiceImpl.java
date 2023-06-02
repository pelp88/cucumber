package ru.coderiders.cucumber.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coderiders.cucumber.enums.TaskStatus;
import ru.coderiders.cucumber.repository.TaskRepository;
import ru.coderiders.cucumber.repository.UserRepository;
import ru.coderiders.cucumber.rest.dto.UserReportDto;
import ru.coderiders.cucumber.rest.exception.NotFoundException;
import ru.coderiders.cucumber.service.ReportService;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private static final String USER_IS_NOT_FOUND_BY_ID_MSG = "Пользователь с id = %d не найден";
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public UserReportDto getUserReport(Long id, LocalDate firstDate, LocalDate secondDate) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(USER_IS_NOT_FOUND_BY_ID_MSG, id)));

        var tasks = taskRepository.findAll().stream()
                .filter(task -> {
                    return (task.getUser() == user)
                            && (task.getStatus() == TaskStatus.CLOSED)
                            && (task.getEndTime().toLocalDate().isAfter(firstDate) && task.getEndTime().toLocalDate().isBefore(secondDate));
                })
                .collect(Collectors.toList());

        return UserReportDto.builder()
                .user(user)
                .userClosedTasks(tasks)
                .build();

    }
}
