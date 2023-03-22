package com.school.auth;

import com.school.api.Authenticate;
import com.school.helpers.Token;
import com.school.objects.Bearer;

import java.io.IOException;
import java.net.URISyntaxException;

public class Login {
    Bearer bearerToken = new Bearer(Token.getBearerToken());

    public AuthStatus authenticate() {
        if (bearerToken != null) {
            if (authenticateViaSavedAccessToken().loggedIn() || authenticateViaRefreshToken().loggedIn()) {
                return AuthStatus.LOGGED_IN;
            }
        }
        return AuthStatus.LOGGED_OUT;
    }

    private AuthStatus authenticateViaSavedAccessToken() {
        try {
            Authenticate.getBearerByAccessToken(bearerToken.getAccessToken());
        } catch (IOException e) {
            return AuthStatus.LOGGED_OUT;
        }
        return AuthStatus.LOGGED_IN;
    }

    private AuthStatus authenticateViaRefreshToken() {
        try {
            Authenticate.getBearerByRefresh(bearerToken.getRefreshToken());
        } catch (IOException | URISyntaxException e) {
            return AuthStatus.LOGGED_OUT;
        }
        return AuthStatus.LOGGED_IN;
    }
}
