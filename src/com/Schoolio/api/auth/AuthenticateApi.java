package com.Schoolio.api.auth;

import com.Schoolio.auth.ValidateInputs;
import com.Schoolio.helpers.BearerToken;
import com.Schoolio.helpers.ConfigFile;
import com.Schoolio.objects.BearerObject;
import com.Schoolio.objects.User;
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
        HttpPost request = new HttpPost(config.getProperty("API_URL") + "secured/authenticate");
        request.addHeader("Authorization", accessToken);

        HttpResponse response;
        try {
            response = client.execute(request);
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
        HttpGet request = new HttpGet(config.getProperty("OKTA_API_URL") + "userinfo");
        request.addHeader("Authorization", "Bearer " + bearer.getAccessToken());

        String userInfo;
        try {
            HttpResponse response = client.execute(request);
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
        if (statusCode != HttpStatus.SC_OK) {
            return new User(false);
        }

        try {
            BearerObject bearerObject = new BearerObject(EntityUtils.toString(response.getEntity()));
            bearerToken.storeToken(bearerObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new User(true);
    }
}
