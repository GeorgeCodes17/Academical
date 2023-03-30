package com.school.helpers;

import com.google.gson.Gson;
import pt.davidafsilva.apple.OSXKeychain;
import pt.davidafsilva.apple.OSXKeychainException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class BearerToken {
    private final GetEmailAddressStoredLocal emailAddressLocal = new GetEmailAddressStoredLocal();

    private final String LOCAL_TOKEN_KEY = "Schoolio";
    private final TokenHandler tokenHandler = new TokenHandler();

    public void storeToken(String bearer) {
        String previousUserEmail = null;
        try {
            if (emailAddressLocal.getEmailAddress().isPresent()) {
                previousUserEmail = emailAddressLocal.getEmailAddress().get();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String idTokenEncoded = new Gson().fromJson(bearer, HashMap.class).get("id_token").toString();
        HashMap<String,String> idTokenDecoded = tokenHandler.decodeJWT(idTokenEncoded);
        HashMap<String, HashMap<String, String>> idTokenWKeys = tokenHandler.JWTToMap(idTokenDecoded);
        String newUserEmail = idTokenWKeys.get("payload").get("email");

        OSXKeychain keychain;
        try {
            keychain = OSXKeychain.getInstance();
            if (emailAddressLocal.getEmailAddress().isPresent()) {
                keychain.deleteGenericPassword(LOCAL_TOKEN_KEY, previousUserEmail);
                keychain.addGenericPassword(LOCAL_TOKEN_KEY, newUserEmail, bearer);
                emailAddressLocal.storeNewEmail(newUserEmail);
                return;
            }
            keychain.addGenericPassword(LOCAL_TOKEN_KEY, newUserEmail, bearer);
            emailAddressLocal.storeNewEmail(newUserEmail);
        } catch (OSXKeychainException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteToken() {
        Optional<String> emailAddress;
        try {
            emailAddress = emailAddressLocal.getEmailAddress();
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
            emailAddress = emailAddressLocal.getEmailAddress();
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
