package com.Academical.helpers;

import com.Academical.Launcher;
import com.Academical.api.auth.AuthenticateApi;
import com.Academical.api.auth.BearerTokenApi;
import com.Academical.objects.BearerObject;
import com.Academical.objects.User;
import org.apache.logging.log4j.Level;

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
            Launcher.logAll(Level.TRACE, e);
            return new User(false);
        }

        return bearerTokenApi.getBearerByRefresh(bearerObject.getRefreshToken());
    }
}
