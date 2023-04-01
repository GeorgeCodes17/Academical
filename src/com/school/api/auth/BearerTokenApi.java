package com.school.api.auth;

import com.school.helpers.ConfigFile;
import com.school.objects.User;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BearerTokenApi {
    private final String apiUrl = new ConfigFile().config().getProperty("API_URL");
    private final com.school.helpers.BearerToken bearerToken = new com.school.helpers.BearerToken();

    public User getBearerByRefresh(String refreshToken) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(apiUrl + "get-bearer-by-refresh");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.addHeader("Authorization", refreshToken);

        HttpResponse response;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            System.out.println("Failed to call get bearer by refresh");
            throw new RuntimeException(e);
        }
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            System.out.println("Failed to auth");
            return new User(false);
        }
        try {
            bearerToken.storeToken(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            System.out.println("Failed to store bearer token");
            throw new RuntimeException(e);
        }
        return new User(true);
    }

    public User getBearerByCreds(String email, String password) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(apiUrl + "get-bearer-by-creds");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", email));
        params.add(new BasicNameValuePair("password", password));

        HttpResponse response;
        try {
            request.setEntity(new UrlEncodedFormEntity(params));
            response = client.execute(request);
        } catch (IOException e) {
            System.out.println("Failed to call endpoint at getBearerByCreds");
            throw new RuntimeException(e);
        }
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            return new User(false);
        }

        try {
            bearerToken.storeToken(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new User(true);
    }
}
