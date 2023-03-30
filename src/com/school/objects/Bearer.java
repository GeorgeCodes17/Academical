package com.school.objects;

import com.google.gson.Gson;

import java.util.HashMap;

public class Bearer {
    private final String bearer;

    public Bearer() {
        bearer = null;
    }

    public Bearer(String bearer) {
        this.bearer = bearer;
    }

    public boolean isNull() {
        return bearer == null;
    }

    public String getAccessToken() {
        if (bearer == null) {
            return "";
        }
        return new Gson().fromJson(bearer, HashMap.class).get("access_token").toString();
    }

    public String getRefreshToken() {
        if (bearer == null) {
            return "";
        }
        return new Gson().fromJson(bearer, HashMap.class).get("refresh_token").toString();
    }
}
