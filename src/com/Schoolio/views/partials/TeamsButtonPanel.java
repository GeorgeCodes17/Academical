package com.Schoolio.views.partials;

import com.Schoolio.views.partials.helpers.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TeamsButtonPanel extends JPanel {
    private final String TEAMS_LOGIN_URL = "https://go.microsoft.com/fwlink/p/?linkid=873020&culture=en-us&country=ww";

    public TeamsButtonPanel() {
        JButton teamsButton = new RoundedJButton("Teams", 180, 40, Colors.TEAMS_PURPLE, Color.WHITE);
        teamsButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI(TEAMS_LOGIN_URL));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        JPanel teamsButtonWrapper = new JPanel();
        teamsButtonWrapper.add(teamsButton);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setSize(800,400);
        setBorder(new EmptyBorder(25, 0, 10, 0));
        add(teamsButtonWrapper);
    }
}
