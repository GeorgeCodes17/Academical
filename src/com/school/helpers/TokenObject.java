package com.school.helpers;

import com.google.gson.Gson;
import com.school.auth.GetEmailAddressStoredLocal;
import pt.davidafsilva.apple.OSXKeychain;
import pt.davidafsilva.apple.OSXKeychainException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class TokenObject {

    private static final String LOCAL_TOKEN_KEY = "Schoolio";

    public static void storeToken(String bearer) {
        String previousUserEmail = null;
        try {
            if (GetEmailAddressStoredLocal.getEmailAddress().isPresent()) {
                previousUserEmail = GetEmailAddressStoredLocal.getEmailAddress().get();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String idTokenEncoded = new Gson().fromJson(bearer, HashMap.class).get("id_token").toString();
        HashMap<String,String> idTokenDecoded = GetTokenHandler.decodeJWT(idTokenEncoded);
        HashMap<String, HashMap<String, String>> idTokenWKeys = GetTokenHandler.JWTToMap(idTokenDecoded);
        String newUserEmail = idTokenWKeys.get("payload").get("email");

        OSXKeychain keychain;
        try {
            keychain = OSXKeychain.getInstance();
            if (GetEmailAddressStoredLocal.getEmailAddress().isPresent()) {
                keychain.deleteGenericPassword(LOCAL_TOKEN_KEY, previousUserEmail);
                keychain.addGenericPassword(LOCAL_TOKEN_KEY, newUserEmail, bearer);
                GetEmailAddressStoredLocal.storeNewEmail(newUserEmail);
                return;
            }
            keychain.addGenericPassword(LOCAL_TOKEN_KEY, newUserEmail, bearer);
            GetEmailAddressStoredLocal.storeNewEmail(newUserEmail);
        } catch (OSXKeychainException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<String> getBearerToken() {
        Optional<String> emailAddress;
        try {
            emailAddress = GetEmailAddressStoredLocal.getEmailAddress();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (emailAddress.isEmpty()) {
            return Optional.empty();
        }

        try {
            OSXKeychain keychain = OSXKeychain.getInstance();
            return keychain.findGenericPassword(LOCAL_TOKEN_KEY, emailAddress.get());
        } catch (OSXKeychainException e) {
            throw new RuntimeException(e);
        }
    }
}
