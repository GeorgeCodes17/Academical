package com.school.helpers;

import com.school.auth.GetEmailAddressStoredLocal;

public class SignOutHandler {
    public void signOut() {
        new BearerToken().deleteToken();
        new GetEmailAddressStoredLocal().deleteEmailAddress();
    }
}
