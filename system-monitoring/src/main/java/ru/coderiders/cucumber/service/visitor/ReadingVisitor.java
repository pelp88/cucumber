package ru.coderiders.cucumber.service.visitor;

import ru.coderiders.cucumber.rest.dto.FieldRsDto;
import ru.coderiders.cucumber.rest.dto.ReadingDto;

public interface ReadingVisitor {
    void visit(ReadingDto reading, FieldRsDto field);
}
