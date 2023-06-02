package ru.coderiders.cucumber.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CannotBePlantedException extends RuntimeException {

    public CannotBePlantedException(String message) {
        super(message);
    }
}
