package com.school.views;

import com.school.helpers.LoginHandler;
import com.school.objects.User;
import com.school.views.partials.TeamsButton;
import com.school.views.partials.Timetable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Dashboard {
    private LoginHandler loginHandler = new LoginHandler();
    public User userInfo = loginHandler.getUserInfo();

    public JPanel displayDashboard() {
        JPanel dashboard = new JPanel();
        dashboard.setLayout(new BorderLayout());
        dashboard.setBorder(BorderFactory.createEmptyBorder(45, 55, 20, 55));

        dashboard.add(getStatusBoard(), BorderLayout.LINE_START);
        dashboard.add(new TeamsButton().getTeamsButton(), BorderLayout.CENTER);
        dashboard.add(new Timetable().getTimetable(), BorderLayout.LINE_END);

        return dashboard;
    }

    private JPanel getStatusBoard() {
        JPanel statusBoard = new JPanel();
        statusBoard.setLayout(new BoxLayout(statusBoard, BoxLayout.Y_AXIS));
        statusBoard.setPreferredSize(new Dimension(300, statusBoard.getPreferredSize().height));

        JLabel welcomeMessage = new JLabel("<html>Welcome, <b>" + userInfo.name() + "</b></html>");
        JLabel firstClassMessage = new JLabel("<html>Your first lesson is " + "<b>Maths</b>" + " at " + "<b>9.00PM</b></html>");
        welcomeMessage.setBorder(new EmptyBorder(0, 0, 30, 0));

        statusBoard.add(welcomeMessage);
        statusBoard.add(firstClassMessage);

        return statusBoard;
    }
}
