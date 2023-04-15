package com.Schoolio.views;

import com.Schoolio.helpers.LoginHandler;
import com.Schoolio.objects.User;
import com.Schoolio.views.account.LoginForm;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private final User user = new LoginHandler().authenticateAndGetUserInfo();

    public static final Font MAIN_FONT = new Font("Roboto", Font.PLAIN, 14);
    public static final JFrame WINDOW = new JFrame();

    public MainWindow() {
        setFonts();

//        WINDOW.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        WINDOW.setVisible(true);
        WINDOW.setTitle("Schoolio");
        WINDOW.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        WINDOW.setSize(800, 500);
        WINDOW.getContentPane().setBackground(Color.white);

        Index index = new Index();
        WINDOW.add(index.getHeaderLabel(), BorderLayout.PAGE_START);

        if(user.signedIn()) {
            WINDOW.add(index.getHeaderLabel(), BorderLayout.PAGE_START);
            WINDOW.add(new Dashboard().displayDashboard(), BorderLayout.CENTER);
            WINDOW.add(index.getFooterLabel(), BorderLayout.PAGE_END);
        } else {
            WINDOW.add(new LoginForm(), BorderLayout.CENTER);
        }

        WINDOW.add(index.getFooterLabel(), BorderLayout.PAGE_END);
    }

    private void setFonts() {
        UIManager.put("Label.font", MAIN_FONT);
        UIManager.put("TextField.font", MAIN_FONT);
        UIManager.put("PasswordField.font", MAIN_FONT);
        UIManager.put("Button.font", MAIN_FONT);
    }

    public void show() {
        WINDOW.setVisible(true);
    }
}