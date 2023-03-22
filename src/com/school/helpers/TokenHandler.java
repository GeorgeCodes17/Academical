package com.school.helpers;

import com.google.gson.Gson;

import java.util.Base64;
import java.util.HashMap;

public class TokenHandler {
    public static HashMap<String,String> decodeJWT(String token) {
        String[] splitToken = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        HashMap<String,String> jwtMap = new HashMap<>();
        jwtMap.put("header", new String(decoder.decode(splitToken[0])));
        jwtMap.put("payload", new String(decoder.decode(splitToken[1])));

        return jwtMap;
    }

    public static HashMap<String, HashMap<String, String>> JWTToMap(HashMap<String,String> token) {
        Base64.Decoder decoder = Base64.getUrlDecoder();

        HashMap<String, HashMap<String,String>> jwtWithKeys = new HashMap<>();

        jwtWithKeys.put("header", new Gson().fromJson(token.get("header"), HashMap.class));
        jwtWithKeys.put("payload", new Gson().fromJson(token.get("payload"), HashMap.class));

        return jwtWithKeys;
    }
}
