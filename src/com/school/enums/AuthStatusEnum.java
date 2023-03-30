package com.school.enums;

public enum AuthStatusEnum {
    LOGGED_IN,
    LOGGED_OUT;

    public boolean loggedIn() {
        return this == LOGGED_IN;
    }

    public boolean loggedOut() {
        return this == LOGGED_OUT;
    }
}
