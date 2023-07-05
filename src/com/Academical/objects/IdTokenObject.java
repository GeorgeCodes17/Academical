package com.Academical.objects;

import com.Academical.helpers.TokenHandler;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class IdTokenObject {
    private final TokenHandler tokenHandler = new TokenHandler();

    private final String atHash;
    private final String sub;
    private final Double ver;
    private final ArrayList<Integer> amr;
    private final String iss;
    private final String preferredUsername;
    private final String aud;
    private final String idp;
    private final Double authTime;
    private final String name;
    private final Double exp;
    private final Double iat;
    private final String email;
    private final String jti;

    private final Gson gson = new Gson();

    public IdTokenObject(BearerObject bearerObject) {
        String idTokenRaw = gson.fromJson(bearerObject.getBearer(), HashMap.class).get("id_token").toString();
        HashMap<String,HashMap<String, String>> idToken = tokenHandler.decodeJWT(idTokenRaw);
        HashMap idTokenPayload = idToken.get("payload");

        atHash = (String) idTokenPayload.get("at_hash");
        sub = (String) idTokenPayload.get("sub");
        ver = (Double) idTokenPayload.get("ver");
        amr = new ArrayList<>();
        iss = (String) idTokenPayload.get("iss");
        preferredUsername = (String) idTokenPayload.get("preferred_username");
        aud = (String) idTokenPayload.get("aud");
        idp = (String) idTokenPayload.get("idp");
        authTime = (Double) idTokenPayload.get("auth_time");
        name = (String) idTokenPayload.get("name");
        exp = (Double) idTokenPayload.get("exp");
        iat = (Double) idTokenPayload.get("iat");
        email = (String) idTokenPayload.get("email");
        jti = (String) idTokenPayload.get("jti");
    }

    public TokenHandler getTokenHandler() {
        return tokenHandler;
    }

    public String getAtHash() {
        return atHash;
    }

    public String getSub() {
        return sub;
    }

    public Double getVer() {
        return ver;
    }

    public ArrayList<Integer> getAmr() {
        return amr;
    }

    public String getIss() {
        return iss;
    }

    public String getPreferredUsername() {
        return preferredUsername;
    }

    public String getAud() {
        return aud;
    }

    public String getIdp() {
        return idp;
    }

    public Double getAuthTime() {
        return authTime;
    }

    public String getName() {
        return name;
    }

    public Double getExp() {
        return exp;
    }

    public Double getIat() {
        return iat;
    }

    public String getEmail() {
        return email;
    }

    public String getJti() {
        return jti;
    }
}
