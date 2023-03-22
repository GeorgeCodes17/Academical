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
}
