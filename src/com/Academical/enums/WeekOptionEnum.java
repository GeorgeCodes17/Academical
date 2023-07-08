package com.Academical.enums;

public enum WeekOptionEnum {
    A("a"),
    B("b");

    private final String value;

    WeekOptionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
