package com.school.auth;

import com.school.auth.enums.AuthStatusEnum;
import com.school.helpers.TokenObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static com.school.api.auth.Authenticate.getBearerByCreds;

public class SignIn {
    public static AuthStatusEnum signIn(ValidateInputs inputs) {
        try {
            HttpResponse response = getBearerByCreds(inputs.email, inputs.password);
            if (response.getStatusLine().getStatusCode() != 200) {
                return AuthStatusEnum.LOGGED_OUT;
            }

            String bearer = EntityUtils.toString(response.getEntity());
            TokenObject.storeToken(bearer);

            return AuthStatusEnum.LOGGED_IN;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
