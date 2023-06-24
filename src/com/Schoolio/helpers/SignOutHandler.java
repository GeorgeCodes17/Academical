package com.Schoolio.helpers;

import com.Schoolio.objects.User;

public class SignOutHandler {
    public User signOut() {
        new BearerToken().deleteToken();
        new EmailAddressStoredLocal().deleteEmailAddress();
        return new User(false);
    }
}
