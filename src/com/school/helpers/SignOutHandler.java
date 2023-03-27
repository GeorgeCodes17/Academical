package com.school.helpers;

import com.school.auth.GetEmailAddressStoredLocal;

public class SignOutHandler {
    public void signOut() {
        new Token().deleteToken();
        new GetEmailAddressStoredLocal().deleteEmailAddress();
    }
}
