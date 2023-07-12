package com.Academical.helpers;

import com.Academical.api.auth.AuthenticateApi;
import com.Academical.api.auth.BearerTokenApi;
import com.Academical.objects.BearerObject;
import com.Academical.objects.User;

import java.io.IOException;
import java.util.Optional;

public class LoginHandler {
    private final BearerObject bearerObject;
    private final BearerTokenApi bearerTokenApi = new BearerTokenApi();
    private final AuthenticateApi authenticateApi = new AuthenticateApi();

    public LoginHandler() {
        Optional<String> bearerToken = new BearerToken().getBearerToken();
        bearerObject = bearerToken.map(BearerObject::new).orElseGet(BearerObject::new);
    }

    public User authenticate() {
        if (!bearerObject.isPresent()) {
            return new User(false);
        }

        try {
            User userInfo = authenticateApi.getUserInfo();
            if (userInfo.loggedIn()) {
                return userInfo;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bearerTokenApi.getBearerByRefresh(bearerObject.getRefreshToken());
    }
}
