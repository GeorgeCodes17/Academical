package com.Schoolio.auth;

import com.Schoolio.Launcher;
import com.Schoolio.enums.ValidateAccountEnum;
import com.Schoolio.exceptions.ValidateInputsException;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class ValidateInputs {
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    private final Pattern emailRegex = Pattern.compile(String.valueOf(Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")));

    public ValidateInputs(String email, char[] password) {
        this.email = email;
        this.password = new String(password);
    }

    public ValidateInputs(String firstName, String lastName, String email, char[] password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = new String(password);
    }

    public Optional<Map<ValidateAccountEnum, Boolean>> validateSignIn() throws ValidateInputsException {
        email = StringEscapeUtils.escapeHtml4(email);
        password = StringEscapeUtils.escapeHtml4(password);

        Map<ValidateAccountEnum, Boolean> errorBag = new HashMap<>();
        if (!emailRegex.matcher(email).matches()) {
            errorBag.put(ValidateAccountEnum.EMAIL, true);
        }
        if (password.equals("pppp") || password.length() < 8 || password.length() > 20) {
            errorBag.put(ValidateAccountEnum.PASSWORD, true);
        }

        if (!errorBag.isEmpty()) {
            ValidateInputsException e = new ValidateInputsException("Sign in input validation failed");
            Launcher.logAll(Level.TRACE, e);
            throw e;
        }
        return Optional.of(errorBag);
    }

    public Optional<Map<ValidateAccountEnum, Boolean>> validateRegister() throws ValidateInputsException {
        firstName = StringEscapeUtils.escapeHtml4(firstName);
        lastName = StringEscapeUtils.escapeHtml4(lastName);
        email = StringEscapeUtils.escapeHtml4(email);

        Map<ValidateAccountEnum, Boolean> errorBag = new HashMap<>();
        if (firstName.equals("First name") || firstName.length() > 30) {
            errorBag.put(ValidateAccountEnum.FIRST_NAME, true);
        }
        if (lastName.equals("Last name") || lastName.length() > 30) {
            errorBag.put(ValidateAccountEnum.LAST_NAME, true);
        }
        if (!emailRegex.matcher(email).matches()) {
            errorBag.put(ValidateAccountEnum.EMAIL, true);
        }
        if (password.equals("pppp") || password.length() < 8 || password.length() > 20) {
            errorBag.put(ValidateAccountEnum.PASSWORD, true);
        }

        if (!errorBag.isEmpty()) {
            ValidateInputsException e = new ValidateInputsException("Register user input validation failed");
            Launcher.logAll(Level.TRACE, e);
            throw e;
        }
        return Optional.of(errorBag);
    }
}
