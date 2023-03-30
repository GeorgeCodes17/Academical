package com.school.objects;

public record User(
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
        this(null, null, null, null, null, null, null, null, 0.0, false);
    }

    public boolean signedIn() {
        return this.sub() != null;
    }
}
