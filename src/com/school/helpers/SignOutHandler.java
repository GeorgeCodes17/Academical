package com.school.helpers;

public class SignOutHandler {
    public void signOut() {
        new BearerToken().deleteToken();
        new GetEmailAddressStoredLocal().deleteEmailAddress();
    }
}
