package ru.coderiders.cucumber.enums;

public enum TaskStatus {
    OPEN("открыта"),
    CLOSED("закрыта"),
    IN_PROGRESS("в процессе");

    public final String message;

    private TaskStatus(String message){
        this.message = message;
    }
}