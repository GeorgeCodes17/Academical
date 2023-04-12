package com.Schoolio.objects;

import com.google.gson.Gson;

import java.util.HashMap;

public class BearerObject {
    private final String bearer;
    private final Gson gson = new Gson();

    public BearerObject() {
        bearer = null;
    }

    public BearerObject(String bearer) {
        this.bearer = bearer;
    }

    public boolean isPresent() {
        return bearer != null;
    }

    public String getAccessToken() {
        return gson.fromJson(bearer, HashMap.class).get("access_token").toString();
    }

    public String getIdToken() {
        return gson.fromJson(bearer, HashMap.class).get("id_token").toString();
    }

    public String getRefreshToken() {
        return gson.fromJson(bearer, HashMap.class).get("refresh_token").toString();
    }
}
