package com.school.objects;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Optional;

public class Bearer {
    private String bearer;

    public Bearer(Optional<String> bearer) {
        this.bearer = bearer.isPresent() ? bearer.get() : null;
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
