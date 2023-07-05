package com.Academical.api.auth;

import com.Academical.Launcher;
import com.Academical.exceptions.SignInUserException;
import com.Academical.exceptions.ValidateInputsException;
import com.Academical.helpers.ConfigFile;
import com.Academical.objects.BearerObject;
import com.Academical.objects.User;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BearerTokenApi {
    private final ConfigFile configFile = new ConfigFile();
    private final String apiUrl = configFile.config().getProperty("ACADEMICAL_API_URL");
    private final com.Academical.helpers.BearerToken bearerToken = new com.Academical.helpers.BearerToken();

    public User getBearerByRefresh(String refreshToken) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(apiUrl + "get-bearer-by-refresh");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.addHeader("Authorization", refreshToken);

        HttpResponse response;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            Launcher.logAll(Level.FATAL, e);
            throw new RuntimeException(e);
        }
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            Launcher.logAll(Level.TRACE, new Exception("Couldn't get bearer token using refresh at BearerTokenApi.getBearerByRefresh"));
            return new User(false);
        }
        try {
            BearerObject bearerObject = new BearerObject(EntityUtils.toString(response.getEntity()));
            bearerToken.storeToken(bearerObject);
        } catch (IOException e) {
            Launcher.logAll(Level.FATAL, e);
            throw new RuntimeException(e);
        }
        return new User(true);
    }

    public User getBearerByCreds(String email, String password) throws SignInUserException, IOException, ValidateInputsException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(apiUrl + "get-bearer-by-creds");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", email));
        params.add(new BasicNameValuePair("password", password));

        HttpResponse response;
        request.setEntity(new UrlEncodedFormEntity(params));
        response = client.execute(request);
        String responseBody = EntityUtils.toString(response.getEntity());

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_BAD_REQUEST) {
            ValidateInputsException e = new ValidateInputsException("Bad request: " + responseBody);
            Launcher.logAll(Level.TRACE, e);
            throw e;
        } else if (statusCode != HttpStatus.SC_OK) {
            SignInUserException e = new SignInUserException("Couldn't get bearer token using user credentials (sign in user)");
            Launcher.logAll(Level.TRACE,  e);
            throw e;
        }

        BearerObject bearerObject = new BearerObject(responseBody);
        bearerToken.storeToken(bearerObject);

        return new User(true);
    }
}
