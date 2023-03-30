package com.school.auth;

import com.school.api.auth.Authenticate;
import com.school.auth.enums.AuthStatusEnum;
import com.school.helpers.BearerToken;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class CreateAccount {
    public AuthStatusEnum createAccount(ValidateInputs inputs) {
        HttpResponse response;
        try {
            response = new Authenticate().registerUser(inputs);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        if (response.getStatusLine().getStatusCode() != 200) {
            return AuthStatusEnum.LOGGED_OUT;
        }

        String bearer;
        try {
            bearer = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new BearerToken().storeToken(bearer);

        return AuthStatusEnum.LOGGED_IN;
    }
}