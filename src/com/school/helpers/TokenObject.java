package com.school.helpers;

import com.google.gson.Gson;
import com.school.auth.GetEmailAddressStoredLocal;
import pt.davidafsilva.apple.OSXKeychain;
import pt.davidafsilva.apple.OSXKeychainException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class TokenObject {

    public static void storeToken(String bearer) {
        String previousUserEmail;
        try {
            previousUserEmail = GetEmailAddressStoredLocal.getEmailAddress().get();
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
            Optional<String> bearerExists = keychain.findGenericPassword("Schoolio", newUserEmail);
            if (GetEmailAddressStoredLocal.getEmailAddress().isPresent()) {
                keychain.deleteGenericPassword("Schoolio", previousUserEmail);
                keychain.addGenericPassword("Schoolio", newUserEmail, bearer);
                GetEmailAddressStoredLocal.storeNewEmail(newUserEmail);
                return;
            }
            keychain.addGenericPassword("Schoolio", newUserEmail, bearer);
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
