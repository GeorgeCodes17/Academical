package com.school.helpers;

import com.school.api.auth.AuthenticateApi;
import com.school.api.auth.BearerTokenApi;
import com.school.objects.Bearer;
import com.school.objects.User;

import java.util.Optional;

public class LoginHandler {
    private final Bearer bearer;
    private final BearerTokenApi bearerToken = new BearerTokenApi();
    private final AuthenticateApi authenticate = new AuthenticateApi();

    public LoginHandler() {
        Optional<String> bearerToken = new BearerToken().getBearerToken();
        bearer = bearerToken.map(Bearer::new).orElseGet(Bearer::new);
    }

    public User authenticateAndGetUserInfo() {
        return authenticate().loggedIn() ? authenticate.getUserInfo() : new User();
    }

    public User authenticate() {
        boolean authenticated = bearer.isPresent() && (authenticate.authorize(bearer.getAccessToken()).signedIn() ||
                bearerToken.getBearerByRefresh(bearer.getRefreshToken()).signedIn());
        return new User(authenticated);
    }
}
