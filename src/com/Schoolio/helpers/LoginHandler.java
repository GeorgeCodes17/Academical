package com.Schoolio.helpers;

import com.Schoolio.api.auth.AuthenticateApi;
import com.Schoolio.api.auth.BearerTokenApi;
import com.Schoolio.objects.BearerObject;
import com.Schoolio.objects.User;

import java.util.Optional;

public class LoginHandler {
    private final BearerObject bearerObject;
    private final BearerTokenApi bearerTokenApi = new BearerTokenApi();
    private final AuthenticateApi authenticateApi = new AuthenticateApi();

    public LoginHandler() {
        Optional<String> bearerToken = new BearerToken().getBearerToken();
        bearerObject = bearerToken.map(BearerObject::new).orElseGet(BearerObject::new);
    }

    public User authenticateAndGetUserInfo() {
        User authed = authenticate();
        return authed.loggedIn() ? authenticateApi.getUserInfo() : authed;
    }

    public User authenticate() {
        boolean authenticated = bearerObject.isPresent() && (authenticateApi.authorize(bearerObject.getAccessToken()).signedIn() ||
                bearerTokenApi.getBearerByRefresh(bearerObject.getRefreshToken()).signedIn());
        return new User(authenticated);
    }
}
