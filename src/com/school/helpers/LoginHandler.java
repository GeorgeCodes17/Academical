package com.school.helpers;

import com.google.gson.Gson;
import com.school.api.auth.Authenticate;
import com.school.auth.enums.AuthStatusEnum;
import com.school.objects.BearerToken;
import com.school.objects.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class LoginHandler {
    static BearerToken bearerToken = new BearerToken(TokenObject.getBearerToken());

    public AuthStatusEnum authenticate() {
        if (bearerToken.get() != null) {
            if (authenticateViaSavedAccessToken().loggedIn() || authenticateViaRefreshToken().loggedIn()) {
                return AuthStatusEnum.LOGGED_IN;
            }
        }
        return AuthStatusEnum.LOGGED_OUT;
    }

    public User getUserInfo() {
        String userInfo;
        try {
            userInfo = Authenticate.getUserInfo(bearerToken.getAccessToken());
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
            Authenticate.authorize(bearerToken.getAccessToken());
        } catch (IOException e) {
            return AuthStatusEnum.LOGGED_OUT;
        }
        return AuthStatusEnum.LOGGED_IN;
    }

    private AuthStatusEnum authenticateViaRefreshToken() {
        try {
            Authenticate.getBearerByRefresh(bearerToken.getRefreshToken());
        } catch (IOException | URISyntaxException e) {
            return AuthStatusEnum.LOGGED_OUT;
        }
        return AuthStatusEnum.LOGGED_IN;
    }
}
