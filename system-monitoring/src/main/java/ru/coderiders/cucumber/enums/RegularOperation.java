package ru.coderiders.cucumber.enums;

public enum RegularOperation {
    WATER("Полить растение"),
    CHEMICAL("Добавить химикаты"),
    MANURE("Добавить удобрения"),
    CUT("Подстричь растение");

    private final String name;

    RegularOperation(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
