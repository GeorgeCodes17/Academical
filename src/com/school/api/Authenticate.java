package com.school.api;

import com.school.helpers.Token;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import static com.school.api.AuthCodes.getAuthCodes;
import static com.school.helpers.ConfigFile.config;

public class Authenticate {
    final static HttpClient client = HttpClientBuilder.create().build();

    public static void getBearerByAccessToken(String accessToken) throws IOException {
        HttpPost getAuthenticated = new HttpPost(config().getProperty("API_URL") + "authenticate");
        getAuthenticated.addHeader("Authorization", accessToken);

        HttpResponse response = client.execute(getAuthenticated);
        StatusLine status = response.getStatusLine();
        if (status.getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpResponseException(status.getStatusCode(), EntityUtils.toString(response.getEntity()));
        }
    }

    public static void getBearerByRefresh(String refreshToken) throws IOException, URISyntaxException {
        HashMap<String,String> oktaCodes = getAuthCodes();

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost getAuthenticated = new HttpPost("https://" + oktaCodes.get("oktaDomain") + "/oauth2/default/v1/token");
        getAuthenticated.addHeader("Content-Type", "application/x-www-form-urlencoded");
        URI uri = new URIBuilder(getAuthenticated.getURI())
                .addParameter("grant_type", "refresh_token")
                .addParameter("client_id", oktaCodes.get("oktaClientId"))
                .addParameter("client_secret", oktaCodes.get("oktaClientSecret"))
                .addParameter("refresh_token", refreshToken)
                .build();
        getAuthenticated.setURI(uri);

        HttpResponse response = client.execute(getAuthenticated);
        StatusLine status = response.getStatusLine();
        if (status.getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpResponseException(status.getStatusCode(), EntityUtils.toString(response.getEntity()));
        }

        Token.storeToken(EntityUtils.toString(response.getEntity()));
    }
}
