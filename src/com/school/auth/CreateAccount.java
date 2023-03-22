package com.school.auth;

import com.school.api.auth.Authenticate;
import org.apache.http.HttpResponse;

import java.io.UnsupportedEncodingException;

import static com.school.helpers.ConfigFile.config;

public class CreateAccount {
    public static HttpResponse createAccount(ValidateInputs inputs) {
        try {
            return Authenticate.registerUser(inputs);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}