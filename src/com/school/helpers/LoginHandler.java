package com.school.helpers;

import com.school.api.auth.AuthenticateApi;
import com.school.api.auth.BearerTokenApi;
import com.school.objects.BearerObject;
import com.school.objects.User;

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
