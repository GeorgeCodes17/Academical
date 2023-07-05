package com.Academical.helpers;

import com.google.gson.Gson;

import java.util.Base64;
import java.util.HashMap;

public class TokenHandler {
    Gson gson = new Gson();

    public HashMap<String,HashMap<String, String>> decodeJWT(String token) {
        String[] splitToken = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        HashMap<String,String> jwtMap = new HashMap<>();
        jwtMap.put("header", new String(decoder.decode(splitToken[0])));
        jwtMap.put("payload", new String(decoder.decode(splitToken[1])));

        HashMap<String, HashMap<String,String>> jwtMapWithObjects = new HashMap<>();

        jwtMapWithObjects.put("header", gson.fromJson(jwtMap.get("header"), HashMap.class));
        jwtMapWithObjects.put("payload", gson.fromJson(jwtMap.get("payload"), HashMap.class));

        return jwtMapWithObjects;
    }
}
