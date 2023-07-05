package com.Academical.views.partials;

import com.Academical.Launcher;
import com.Academical.views.partials.helpers.Colors;
import org.apache.logging.log4j.Level;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TeamsButtonPanel {
    private final String TEAMS_LOGIN_URL = "https://go.microsoft.com/fwlink/p/?linkid=873020&culture=en-us&country=ww";

    public JPanel getTeamsPanel() {
        JButton teamsButton = new RoundedJButton("Teams", 160, 40, Colors.TEAMS_PURPLE, Color.WHITE);
        teamsButton.addActionListener(event -> {
            try {
                Desktop.getDesktop().browse(new URI(TEAMS_LOGIN_URL));
            } catch (IOException | URISyntaxException e) {
                Launcher.logAll(Level.DEBUG, e);
                throw new RuntimeException(e);
            }
        });

        JPanel teamsButtonWrapper = new JPanel();
        teamsButtonWrapper.add(teamsButton);

        JPanel teamsButtonContainer = new JPanel();
        teamsButtonContainer.setLayout(new BoxLayout(teamsButtonContainer, BoxLayout.X_AXIS));
        teamsButtonContainer.setSize(800,400);
        teamsButtonContainer.setBorder(new EmptyBorder(25, 0, 10, 0));
        teamsButtonContainer.add(teamsButtonWrapper);

        return teamsButtonContainer;
    }
}
