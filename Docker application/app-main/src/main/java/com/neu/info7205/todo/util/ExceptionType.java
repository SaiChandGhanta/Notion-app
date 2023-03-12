package com.neu.info7205.todo.util;

public enum ExceptionType {
    DEFAULT("Default"),
    CODING("Coding"),
    INPUT("Input"),
    CONFIG("Config"),
    USER_CONFIG("UserConfig"),
    INFRA("Infra"),
    PERMISSION("Permission");

    private final String name;

    private ExceptionType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
