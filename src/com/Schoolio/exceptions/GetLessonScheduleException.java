package com.Schoolio.exceptions;

public class GetLessonScheduleException extends Exception {
    public GetLessonScheduleException(String message) {
        super(message);
    }

    public GetLessonScheduleException(Exception message) {
        super(message);
    }
}
