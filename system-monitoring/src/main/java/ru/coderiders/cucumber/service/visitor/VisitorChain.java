package ru.coderiders.cucumber.service.visitor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.coderiders.cucumber.rest.dto.FieldRsDto;
import ru.coderiders.cucumber.rest.dto.ReadingDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VisitorChain {

    private final List<ReadingVisitor> visitors;

    public void visit(ReadingDto reading, FieldRsDto fieldRsDto) {
        visitors.forEach(visitor -> visitor.visit(reading, fieldRsDto));
    }
}
