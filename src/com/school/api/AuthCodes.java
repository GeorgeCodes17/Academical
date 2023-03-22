package com.school.api;

import com.google.gson.Gson;
import com.school.helpers.AdvancedEncryptionStandard;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;

import static com.school.helpers.ConfigFile.config;

public class AuthCodes {
    private static final String DECRYPT_KEY = "a08Uxi8iTqai";

    /**
     * @return HashMap of the okta auth codes needed to perform auth of users
     * @throws IOException if fail to get auth codes or fails to convert response entity to string
     */
    public static HashMap<String,String> getAuthCodes() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet getOktaCodes = new HttpGet(config().getProperty("API_URL") + "get-okta-codes");
        HttpResponse response = client.execute(getOktaCodes);

        StatusLine status = response.getStatusLine();
        if (status.getStatusCode() < HttpStatus.SC_OK || status.getStatusCode() >= HttpStatus.SC_BAD_REQUEST) {
            throw new HttpResponseException(status.getStatusCode(), "Could not get secrets needed for Okta auth");
        }

        String responseAsJson = EntityUtils.toString(response.getEntity());
        HashMap authCodes = new Gson().fromJson(responseAsJson, HashMap.class);
        return processAuthCodes(authCodes);
    }

    private static HashMap<String,String> processAuthCodes(HashMap<String,String> authCodes) {
        authCodes.forEach((oktaKey, oktaToken) -> {
            String oktaTokenDecrypted = AdvancedEncryptionStandard.decrypt(oktaToken, DECRYPT_KEY);
            authCodes.put(oktaKey, oktaTokenDecrypted);
        });
        return authCodes;
    }
}
