package com.Academical.api.auth;

import com.Academical.Launcher;
import com.Academical.auth.ValidateInputs;
import com.Academical.exceptions.GetUserInfoException;
import com.Academical.exceptions.RegisterUserException;
import com.Academical.exceptions.ValidateInputsException;
import com.Academical.helpers.BearerToken;
import com.Academical.helpers.ConfigFile;
import com.Academical.objects.BearerObject;
import com.Academical.objects.User;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AuthenticateApi {
    private final HttpClient client = HttpClientBuilder.create().build();
    private final ConfigFile configFile = new ConfigFile();
    private final Properties config = configFile.config();
    private final BearerToken bearerToken = new BearerToken();

    public User authenticate(String accessToken) {
        HttpPost request = new HttpPost(config.getProperty("ACADEMICAL_API_URL") + "secured/authenticate");
        request.addHeader("Authorization", accessToken);

        HttpResponse response;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            Launcher.logAll(Level.FATAL, e);
            throw new RuntimeException(e);
        }
        return new User(response.getStatusLine().getStatusCode() == 200);
    }

    public User getUserInfo(String accessToken) throws IOException {
        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD)
                .build();
        HttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .build();
        HttpGet request = new HttpGet(config.getProperty("OKTA_API_URL") + "userinfo");
        request.addHeader("Authorization", "Bearer " + accessToken);

        String responseBody;
        HttpResponse response = client.execute(request);
        responseBody = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            Launcher.logAll(Level.TRACE, new GetUserInfoException("Failed to get user info at AuthenticateApi.getUserInfo: " + responseBody));
            return new User(false);
        }
        return new User().mapUserInfoObject(responseBody);
    }

    public User registerUser(ValidateInputs credentials) throws IOException, RegisterUserException, ValidateInputsException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(config.getProperty("ACADEMICAL_API_URL") + "register");
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", credentials.email));
        params.add(new BasicNameValuePair("password", credentials.password));
        params.add(new BasicNameValuePair("first_name", credentials.firstName));
        params.add(new BasicNameValuePair("last_name", credentials.lastName));

        request.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = client.execute(request);
        String responseBody = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_BAD_REQUEST) {
            Launcher.logAll(Level.TRACE, new ValidateInputsException("Bad request at AuthenticateApi.registerUser: " + responseBody));
            throw new ValidateInputsException("AuthenticateApi.registerUser: " + responseBody);
        } else if (statusCode != HttpStatus.SC_OK) {
            Launcher.logAll(Level.INFO, new RegisterUserException("Failed to register user at AuthenticateApi.registerUser: " + responseBody));
            throw new RegisterUserException(responseBody);
        }

        BearerObject bearerObject = new BearerObject(responseBody);
        bearerToken.storeToken(bearerObject);
        return new User(true);
    }
}
