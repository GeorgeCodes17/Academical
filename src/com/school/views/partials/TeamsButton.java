package com.school.views.partials;

import com.school.views.partials.helpers.Colors;
import com.school.views.partials.helpers.RoundedBorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TeamsButton {
    private final String TEAMS_ICON_PATH = "src/com/school/views/assets/chat.png";

    private final String TEAMS_LOGIN_URL = "https://go.microsoft.com/fwlink/p/?linkid=873020&culture=en-us&country=ww";

    public JPanel getTeamsButton() {
        JButton teamsButton = new JButton("Teams", new ImageIcon(TEAMS_ICON_PATH));
        teamsButton.setFont(new Font("Roboto", Font.PLAIN, 14));
        teamsButton.setBackground(Colors.airSuperiorityBlue75);
        teamsButton.setBorder(new RoundedBorder(15, Colors.darkLava));
        teamsButton.setPreferredSize(new Dimension(200,65));
        teamsButton.setIconTextGap(60);
        teamsButton.setHorizontalTextPosition(SwingConstants.LEFT);

        teamsButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI(TEAMS_LOGIN_URL));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
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
