package com.Academical.helpers;

import com.Academical.api.auth.AuthenticateApi;
import com.Academical.api.auth.BearerTokenApi;
import com.Academical.objects.BearerObject;
import com.Academical.objects.User;

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
        boolean authenticated = bearerObject.isPresent() && (authenticateApi.authenticate(bearerObject.getAccessToken()).signedIn() ||
                bearerTokenApi.getBearerByRefresh(bearerObject.getRefreshToken()).signedIn());
        return new User(authenticated);
    }
}
