package com.Schoolio.api.auth;

import com.Schoolio.Launcher;
import com.Schoolio.auth.ValidateInputs;
import com.Schoolio.exceptions.GetUserInfoException;
import com.Schoolio.exceptions.RegisterUserException;
import com.Schoolio.exceptions.ValidateInputsException;
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
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class AuthenticateApi {
    private final HttpClient client = HttpClientBuilder.create().build();
    private final ConfigFile configFile = new ConfigFile();
    private final Properties config = configFile.config();
    private final BearerToken bearerToken = new BearerToken();

    public User authenticate(String accessToken) {
        HttpPost request = new HttpPost(config.getProperty("API_URL") + "secured/authenticate");
        request.addHeader("Authorization", accessToken);

        HttpResponse response;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            Launcher.logAll(Level.FATAL, e.getMessage());
            throw new RuntimeException(e);
        }
        return new User(response.getStatusLine().getStatusCode() == 200);
    }

    public User getUserInfo() throws GetUserInfoException, IOException {
        Optional<String> bearerRaw = new BearerToken().getBearerToken();
        BearerObject bearer = bearerRaw.map(BearerObject::new).orElseGet(BearerObject::new);
        if (bearerRaw.isEmpty()) {
            Launcher.logAll(Level.TRACE, "User is not logged in");
            throw new GetUserInfoException("User is not logged in");
        }

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(config.getProperty("OKTA_API_URL") + "userinfo");
        request.addHeader("Authorization", "Bearer " + bearer.getAccessToken());

        String responseBody;
        HttpResponse response = client.execute(request);
        responseBody = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            Launcher.logAll(Level.WARN, "Failed to get user info at AuthenticateApi.getUserInfo: " + responseBody);
            throw new GetUserInfoException(responseBody);
        }
        return new User().mapUserInfoObject(responseBody);
    }

    public User registerUser(ValidateInputs credentials) throws IOException, RegisterUserException, ValidateInputsException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(config.getProperty("API_URL") + "register");
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
            Launcher.logAll(Level.TRACE, "Bad request at AuthenticateApi.registerUser: " + responseBody);
            throw new ValidateInputsException("AuthenticateApi.registerUser: " + responseBody);
        } else if (statusCode != HttpStatus.SC_OK) {
            Launcher.logAll(Level.INFO, "Failed to register user at AuthenticateApi.registerUser: " + responseBody);
            throw new RegisterUserException(responseBody);
        }

        BearerObject bearerObject = new BearerObject(responseBody);
        bearerToken.storeToken(bearerObject);
        return new User(true);
    }
}
