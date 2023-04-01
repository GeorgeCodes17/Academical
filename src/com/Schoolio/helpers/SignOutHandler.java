package com.Schoolio.helpers;

public class SignOutHandler {
    public void signOut() {
        new BearerToken().deleteToken();
        new emailAddressStoredLocal().deleteEmailAddress();
    }
}
