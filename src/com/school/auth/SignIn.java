package com.school.auth;

import com.school.api.auth.Authenticate;
import com.school.auth.enums.AuthStatusEnum;
import com.school.helpers.BearerToken;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class SignIn {
    public AuthStatusEnum signIn(ValidateInputs inputs) {
        try {
            HttpResponse response = new Authenticate().getBearerByCreds(inputs.email, inputs.password);
            if (response.getStatusLine().getStatusCode() != 200) {
                return AuthStatusEnum.LOGGED_OUT;
            }

            String bearer = EntityUtils.toString(response.getEntity());
            new BearerToken().storeToken(bearer);

            return AuthStatusEnum.LOGGED_IN;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
