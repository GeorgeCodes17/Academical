package com.school.helpers;

import com.school.auth.GetEmailAddressStoredLocal;

public class SignOutHandler {
    public void signOut() {
        Token.deleteToken();
        GetEmailAddressStoredLocal.deleteEmailAddress();
    }
}
