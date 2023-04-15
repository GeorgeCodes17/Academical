package com.Schoolio.helpers;

import com.Schoolio.objects.BearerObject;
import pt.davidafsilva.apple.OSXKeychain;
import pt.davidafsilva.apple.OSXKeychainException;

import java.io.IOException;
import java.util.Optional;

public class BearerToken {
    private final EmailAddressStoredLocal emailAddressStoredLocal = new EmailAddressStoredLocal();
    private final String LOCAL_TOKEN_KEY = "Schoolio";

    public void storeToken(BearerObject bearerObject) {
        String newUserEmail = bearerObject.getIdToken().getEmail();

        OSXKeychain keychain;
        try {
            String previousUserEmail = null;
            if (emailAddressStoredLocal.getEmailAddress().isPresent()) {
                previousUserEmail = emailAddressStoredLocal.getEmailAddress().get();
            }
            keychain = OSXKeychain.getInstance();
            if (emailAddressStoredLocal.getEmailAddress().isPresent()) {
                keychain.deleteGenericPassword(LOCAL_TOKEN_KEY, previousUserEmail);
                keychain.addGenericPassword(LOCAL_TOKEN_KEY, newUserEmail, bearerObject.getBearer());
                emailAddressStoredLocal.storeNewEmail(newUserEmail);
                return;
            }
            keychain.addGenericPassword(LOCAL_TOKEN_KEY, newUserEmail, bearerObject.getBearer());
            emailAddressStoredLocal.storeNewEmail(newUserEmail);
        } catch (OSXKeychainException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteToken() {
        Optional<String> emailAddress;
        try {
            emailAddress = emailAddressStoredLocal.getEmailAddress();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OSXKeychain keychain;
        try {
            keychain = OSXKeychain.getInstance();
            if (emailAddress.isPresent()) {
                keychain.deleteGenericPassword("Schoolio", emailAddress.get());
            } else {
                System.out.println("Not logged in");
            }
        } catch (OSXKeychainException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<String> getBearerToken() {
        Optional<String> emailAddress;
        try {
            emailAddress = emailAddressStoredLocal.getEmailAddress();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (emailAddress.isEmpty()) {
            return emailAddress;
        }

        try {
            OSXKeychain keychain = OSXKeychain.getInstance();
            return keychain.findGenericPassword(LOCAL_TOKEN_KEY, emailAddress.get());
        } catch (OSXKeychainException e) {
            throw new RuntimeException(e);
        }
    }
}
