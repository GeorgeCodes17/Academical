package com.school.views.partials.helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public final class AddPlaceholders {
    public AddPlaceholders(JTextField textField, FocusEvent e, String placeholderText) {
        forField(textField, e, placeholderText);
    }

    public AddPlaceholders(JPasswordField passwordField, FocusEvent e, String placeholderText) {
        forPassword(passwordField, e, placeholderText);
    }

    public JTextField forField(JTextField textField, FocusEvent e, String placeholderText) {
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholderText)) {
                    textField.setForeground(Color.BLACK);
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholderText);
                }
            }
        });

        return textField;
    };

    public JPasswordField forPassword(JPasswordField passwordField, FocusEvent e, String placeholderText) {
        passwordField.setForeground(Color.GRAY);
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String passwordInput = new String(passwordField.getPassword());
                if (passwordInput.equals(placeholderText)) {
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText(placeholderText);
                }
            }
        });

        return passwordField;
    };
}
