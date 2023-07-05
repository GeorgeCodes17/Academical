package com.Academical.helpers;

import com.Academical.objects.User;

public class SignOutHandler {
    public User signOut() {
        new BearerToken().deleteToken();
        new EmailAddressStoredLocal().deleteEmailAddress();
        return new User(false);
    }
}
