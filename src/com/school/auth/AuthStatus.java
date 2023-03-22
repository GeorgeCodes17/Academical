package com.school.auth;

public enum AuthStatus {
    LOGGED_IN,
    LOGGED_OUT;

    public boolean loggedIn() {
        return this == LOGGED_IN;
    }

    public boolean loggedOut() {
        return this == LOGGED_OUT;
    }
}
