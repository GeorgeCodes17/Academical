package com.Schoolio.views;

import com.Schoolio.api.auth.AuthenticateApi;
import com.Schoolio.objects.User;
import com.Schoolio.views.partials.TeamsButtonPanel;
import com.Schoolio.views.partials.Timetable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Dashboard extends JPanel {
    private final User userInfo = new AuthenticateApi().getUserInfo();

    public Dashboard() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(45, 55, 20, 55));

        add(getStatusBoard(), BorderLayout.LINE_START);
        add(new TeamsButtonPanel().getTeamsPanel(), BorderLayout.CENTER);
        add(new Timetable().getTimetable(), BorderLayout.LINE_END);
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
