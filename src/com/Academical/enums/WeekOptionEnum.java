package com.Academical.enums;

public enum WeekOptionEnum {
    A("a"),
    B("b");

    private final String value;

    WeekOptionEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static WeekOptionEnum fromString(String text) {
        return switch (text) {
            case "b" -> WeekOptionEnum.B;
            default -> WeekOptionEnum.A;
        };
    }
}
