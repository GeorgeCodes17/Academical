package com.school.api.auth;

import com.school.auth.ValidateInputs;
import com.school.helpers.BearerToken;
import com.school.helpers.ConfigFile;
import com.school.objects.BearerObject;
import com.school.objects.User;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class AuthenticateApi {
    private final HttpClient client = HttpClientBuilder.create().build();
    private final Properties config = new ConfigFile().config();
    private final BearerToken bearerToken = new BearerToken();

    public User authorize(String accessToken) {
        HttpPost getAuthenticated = new HttpPost(config.getProperty("API_URL") + "authenticate");
        getAuthenticated.addHeader("Authorization", accessToken);

        HttpResponse response;
        try {
            response = client.execute(getAuthenticated);
        } catch (IOException e) {
            System.out.println("IOException in authorize");
            throw new RuntimeException(e);
        }
        return new User(response.getStatusLine().getStatusCode() == 200);
    }

    public User getUserInfo() {
        Optional<String> bearerRaw = new BearerToken().getBearerToken();
        BearerObject bearer = bearerRaw.map(BearerObject::new).orElseGet(BearerObject::new);

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet getUserInfo = new HttpGet(config.getProperty("OKTA_API_URL") + "userinfo");
        getUserInfo.addHeader("Authorization", "Bearer " + bearer.getAccessToken());

        String userInfo;
        try {
            HttpResponse response = client.execute(getUserInfo);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                System.out.println("Failed to getUserInfo");
                return new User(false);
            }
            userInfo = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new User().mapUserInfoObject(userInfo);
    }

    public User registerUser(ValidateInputs credentials) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(config.getProperty("API_URL") + "register");
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", credentials.email));
        params.add(new BasicNameValuePair("password", credentials.password));
        params.add(new BasicNameValuePair("first_name", credentials.firstName));
        params.add(new BasicNameValuePair("last_name", credentials.lastName));

        HttpResponse response;
        try {
            request.setEntity(new UrlEncodedFormEntity(params));
            response = client.execute(request);
        } catch (IOException e) {
            System.out.println("Failed to call endpoint at registerUser");
            throw new RuntimeException(e);
        }
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            return new User(false);
        }

        String bearer;
        try {
            bearer = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bearerToken.storeToken(bearer);
        return new User(true);
    }
}
