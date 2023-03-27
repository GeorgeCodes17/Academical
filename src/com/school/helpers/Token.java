package com.school.helpers;

import com.google.gson.Gson;
import com.school.auth.GetEmailAddressStoredLocal;
import pt.davidafsilva.apple.OSXKeychain;
import pt.davidafsilva.apple.OSXKeychainException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class Token {
    private final GetEmailAddressStoredLocal emailAddressLocal = new GetEmailAddressStoredLocal();
    private final TokenHandler tokenHandler = new TokenHandler();

    public void storeToken(String bearer) {
        String idTokenEncoded = new Gson().fromJson(bearer, HashMap.class).get("id_token").toString();
        HashMap<String,String> idTokenDecoded = tokenHandler.decodeJWT(idTokenEncoded);
        HashMap<String, HashMap<String, String>> idTokenWKeys = tokenHandler.JWTToMap(idTokenDecoded);
        String userEmail = idTokenWKeys.get("payload").get("email");

        OSXKeychain keychain;
        try {
            keychain = OSXKeychain.getInstance();
            Optional<String> bearerExists = keychain.findGenericPassword("Schoolio", userEmail);
            if (bearerExists.isPresent()) {
                keychain.modifyGenericPassword("Schoolio", userEmail, bearer);
                emailAddressLocal.storeNewEmail(userEmail);
                return;
            }
            keychain.addGenericPassword("Schoolio", userEmail, bearer);
            emailAddressLocal.storeNewEmail(userEmail);
        } catch (OSXKeychainException e) {
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
            keychain.deleteGenericPassword("Schoolio", emailAddress.get());
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
        if (!emailAddress.isPresent()) {
            return Optional.empty();
        }

        try {
            OSXKeychain keychain = OSXKeychain.getInstance();
            return keychain.findGenericPassword("Schoolio", emailAddress.get());
        } catch (OSXKeychainException e) {
            throw new RuntimeException(e);
        }
    }
}
