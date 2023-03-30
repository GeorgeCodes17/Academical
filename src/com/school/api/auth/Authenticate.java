package com.school.api.auth;

import com.school.auth.ValidateInputs;
import com.school.helpers.ConfigFile;
import com.school.helpers.BearerToken;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Authenticate {
    final HttpClient client = HttpClientBuilder.create().build();
    final Properties config = new ConfigFile().config();
    final String API_URL = config.getProperty("API_URL");
    final String OKTA_API_URL = config.getProperty("OKTA_API_URL");

    public void authorize(String accessToken) throws IOException {
        HttpPost getAuthenticated = new HttpPost(API_URL + "authenticate");
        getAuthenticated.addHeader("Authorization", accessToken);

        HttpResponse response = client.execute(getAuthenticated);
        StatusLine status = response.getStatusLine();
        if (status.getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpResponseException(status.getStatusCode(), EntityUtils.toString(response.getEntity()));
        }
    }

    public String getUserInfo(String accessToken) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet getUserInfo = new HttpGet(OKTA_API_URL + "userinfo");
        getUserInfo.addHeader("Authorization", "Bearer " + accessToken);

        HttpResponse response = client.execute(getUserInfo);
        StatusLine status = response.getStatusLine();
        if (status.getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpResponseException(status.getStatusCode(), EntityUtils.toString(response.getEntity()));
        }
        return EntityUtils.toString(response.getEntity());
    }

    public void getBearerByRefresh(String refreshToken) throws IOException, URISyntaxException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost getBearer = new HttpPost(API_URL + "get-bearer-by-refresh");
        getBearer.addHeader("Content-Type", "application/x-www-form-urlencoded");
        getBearer.addHeader("Authorization", refreshToken);

        HttpResponse response = client.execute(getBearer);
        StatusLine status = response.getStatusLine();
        if (status.getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpResponseException(status.getStatusCode(), EntityUtils.toString(response.getEntity()));
        }

        new BearerToken().storeToken(EntityUtils.toString(response.getEntity()));
    }

    public HttpResponse getBearerByCreds(String email, String password) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(API_URL + "get-bearer-by-creds");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", email));
        params.add(new BasicNameValuePair("password", password));

        request.setEntity(new UrlEncodedFormEntity(params));
        return client.execute(request);
    }

    public HttpResponse registerUser(ValidateInputs credentials) throws UnsupportedEncodingException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(API_URL + "register");
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", credentials.email));
        params.add(new BasicNameValuePair("password", credentials.password));
        params.add(new BasicNameValuePair("first_name", credentials.firstName));
        params.add(new BasicNameValuePair("last_name", credentials.lastName));
        request.setEntity(new UrlEncodedFormEntity(params));

        try {
            return client.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
