package com.Academical.helpers;

import com.Academical.Launcher;
import com.Academical.objects.BearerObject;
import org.apache.logging.log4j.Level;
import pt.davidafsilva.apple.OSXKeychain;
import pt.davidafsilva.apple.OSXKeychainException;

import java.io.IOException;
import java.util.Optional;

public class BearerToken {
    private final EmailAddressStoredLocal emailAddressStoredLocal = new EmailAddressStoredLocal();
    private final String LOCAL_TOKEN_KEY = "Academical";

    public void storeToken(BearerObject bearerObject) {
        String newUserEmail = bearerObject.getIdToken().getEmail();

        OSXKeychain keychain;
        try {
            keychain = OSXKeychain.getInstance();
            if (emailAddressStoredLocal.getEmailAddress().isPresent()) {
                String previousUserEmail = emailAddressStoredLocal.getEmailAddress().get();
                keychain.deleteGenericPassword(LOCAL_TOKEN_KEY, previousUserEmail);
                keychain.addGenericPassword(LOCAL_TOKEN_KEY, newUserEmail, bearerObject.getBearer());
                emailAddressStoredLocal.storeNewEmail(newUserEmail);
                return;
            }
            keychain.addGenericPassword(LOCAL_TOKEN_KEY, newUserEmail, bearerObject.getBearer());
            emailAddressStoredLocal.storeNewEmail(newUserEmail);
        } catch (OSXKeychainException | IOException e) {
            Launcher.logAll(Level.FATAL, e);
            throw new RuntimeException(e);
        }
    }

    public void deleteToken() {
        String emailAddress;
        try {
            emailAddress = emailAddressStoredLocal.getEmailAddress().isPresent() ? emailAddressStoredLocal.getEmailAddress().get() : null;
        } catch (IOException e) {
            Launcher.logAll(Level.FATAL, e);
            throw new RuntimeException(e);
        }
        OSXKeychain keychain;
        try {
            keychain = OSXKeychain.getInstance();
            keychain.deleteGenericPassword("Academical", emailAddress);
        } catch (OSXKeychainException e) {
            Launcher.logAll(Level.FATAL, e);
            throw new RuntimeException(e);
        }
    }

    public Optional<String> getBearerToken() {
        Optional<String> emailAddress;
        try {
            emailAddress = emailAddressStoredLocal.getEmailAddress();
        } catch (IOException e) {
            Launcher.logAll(Level.FATAL, e);
            throw new RuntimeException(e);
        }
        if (emailAddress.isEmpty()) {
            return emailAddress;
        }

        try {
            OSXKeychain keychain = OSXKeychain.getInstance();
            return keychain.findGenericPassword(LOCAL_TOKEN_KEY, emailAddress.get());
        } catch (OSXKeychainException e) {
            Launcher.logAll(Level.FATAL, e);
            throw new RuntimeException(e);
        }
    }
}
