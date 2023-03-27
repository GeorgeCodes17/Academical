package com.school.views;

import com.school.auth.enums.AuthStatusEnum;
import com.school.helpers.LoginHandler;
import com.school.objects.User;
import com.school.views.account.LoginForm;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    public AuthStatusEnum auth = new LoginHandler().authenticate();

    public User userInfo = null;
    {
        if (auth.loggedIn()) {
            userInfo = new LoginHandler().getUserInfo();
        }
    }

    public final JFrame WINDOW;
    final Font MAIN_FONT = new Font("Roboto", Font.PLAIN, 14);
    public MainWindow() {
        UIManager.getDefaults().put(new JButton().getUIClassID(), "com.school.views.partials.helpers.RoundedButton");
        setFonts();

        WINDOW = new JFrame();
        WINDOW.setExtendedState(JFrame.MAXIMIZED_BOTH);
        WINDOW.setVisible(true);
        WINDOW.setTitle("Schoolio");
        WINDOW.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        WINDOW.setSize(800, 500);
        WINDOW.getContentPane().setBackground(Color.white);

        WINDOW.add(new Index().getHeaderLabel(auth), BorderLayout.PAGE_START);

        if(auth.loggedIn()) {
            WINDOW.add(new Dashboard(userInfo).displayDashboard(), BorderLayout.CENTER);
        } else {
            System.out.println("Logged out");
            WINDOW.add(new LoginForm());
        }

        WINDOW.add(new Index().getFooterLabel(), BorderLayout.PAGE_END);
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