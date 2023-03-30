package com.school.helpers;

import com.google.gson.Gson;
import com.school.api.auth.Authenticate;
import com.school.auth.enums.AuthStatusEnum;
import com.school.objects.Bearer;
import com.school.objects.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

public class LoginHandler {
    private final Bearer bearer;

    public LoginHandler() {
        Optional<String> bearerToken = new BearerToken().getBearerToken();
        bearer = bearerToken.map(Bearer::new).orElseGet(Bearer::new);
    }

    public User authenticateAndGetUserInfo() {
        return authenticate().loggedIn() ? getUserInfo() : new User();
    }

    public AuthStatusEnum authenticate() {
        if (bearer.isNull() && (authenticateViaSavedAccessToken().loggedIn() ||
                authenticateViaRefreshToken().loggedIn())) {
            return AuthStatusEnum.LOGGED_IN;
        }
        return AuthStatusEnum.LOGGED_OUT;
    }

    public User getUserInfo() {
        String userInfo;
        try {
            userInfo = new Authenticate().getUserInfo(bearer.getAccessToken());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mapUserInfoObject(userInfo);
    }

    private User mapUserInfoObject(String userInfo) {
        Map userMap = new Gson().fromJson(userInfo, Map.class);

        return new User(
                (String) userMap.get("sub"),
                (String) userMap.get("name"),
                (String) userMap.get("locale"),
                (String) userMap.get("email"),
                (String) userMap.get("preferred_username"),
                (String) userMap.get("given_name"),
                (String) userMap.get("family_name"),
                (String) userMap.get("zoneinfo"),
                (double) userMap.get("updated_at"),
                (boolean) userMap.get("email_verified")
        );
    }

    private AuthStatusEnum authenticateViaSavedAccessToken() {
        try {
            new Authenticate().authorize(bearer.getAccessToken());
        } catch (IOException e) {
            return AuthStatusEnum.LOGGED_OUT;
        }
        return AuthStatusEnum.LOGGED_IN;
    }

    private AuthStatusEnum authenticateViaRefreshToken() {
        try {
            new Authenticate().getBearerByRefresh(bearer.getRefreshToken());
        } catch (IOException | URISyntaxException e) {
            return AuthStatusEnum.LOGGED_OUT;
        }
        return AuthStatusEnum.LOGGED_IN;
    }
}
