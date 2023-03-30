package com.school.objects;

import com.google.gson.Gson;

import java.util.Map;

public record User(
        boolean loggedIn,
        String sub,
        String name,
        String locale,
        String email,
        String preferredUsername,
        String givenName,
        String familyName,
        String zoneInfo,
        double updatedAt,
        boolean emailVerified
) {
    public User() {
        this(false, null, null, null, null, null, null, null, null, 0.0, false);
    }

    public User(boolean loggedIn) {
        this(loggedIn, null, null, null, null, null, null, null, null, 0.0, false);
    }

    public boolean signedIn() {
        return this.loggedIn;
    }

    public boolean signedOut() {
        return this.loggedIn;
    }

    public User mapUserInfoObject(String userInfo) {
        Map userMap = new Gson().fromJson(userInfo, Map.class);

        return new User(
                true,
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
}
