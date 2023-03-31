package com.school.objects;

import com.google.gson.Gson;

import java.util.HashMap;

public class BearerObject {
    private final String BEARER;

    public BearerObject() {
        BEARER = null;
    }

    public BearerObject(String bearer) {
        this.BEARER = bearer;
    }

    public boolean isPresent() {
        return BEARER != null;
    }

    public boolean isEmpty() {
        return BEARER == null;
    }

    public String getAccessToken() {
        if (BEARER == null) {
            return "";
        }
        return new Gson().fromJson(BEARER, HashMap.class).get("access_token").toString();
    }

    public String getRefreshToken() {
        if (BEARER == null) {
            return "";
        }
        return new Gson().fromJson(BEARER, HashMap.class).get("refresh_token").toString();
    }
}
