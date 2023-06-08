package com.Schoolio.objects;

public record LessonItem(String text, String name, YearObject yearObject) {
    @Override
    public String toString() {
        return text;
    }
}
