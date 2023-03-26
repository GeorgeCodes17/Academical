package com.school.views;

import com.school.auth.enums.AuthStatusEnum;
import com.school.helpers.SignOutHandler;
import com.school.views.partials.helpers.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Index {
    public static JPanel getHeaderLabel(AuthStatusEnum auth) {
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(Colors.middleBlue);

        JLabel heading = new JLabel("Schoolio", SwingConstants.CENTER);
        heading.setBackground(Colors.middleBlue);
        heading.setFont(new Font("Roboto", Font.PLAIN, 32));
        heading.setBorder(new EmptyBorder(10,0,10,0));

        header.add(heading, BorderLayout.CENTER);
        if (auth.loggedIn()) {
            header.add(getSignOutLink(), BorderLayout.LINE_END);
        }
        return header;
    }

    public static JLabel getFooterLabel() {
        JLabel footer = new JLabel("Schoolio is a company, established in 2022, created by a developer who couldn't think of a good project to do", SwingConstants.CENTER);
        footer.setBackground(Colors.middleBlue);
        footer.setOpaque(true);
        footer.setBorder(new EmptyBorder(10,0,10, 0));
        return footer;
    }

    public static JLabel getSignOutLink() {
        JLabel signOutLink = new JLabel("<html>Sign Out -></html>");
        signOutLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signOutLink.setBorder(new EmptyBorder(0, 0, 0, 25));
        signOutLink.setFont(new Font("Roboto", Font.BOLD | Font.ITALIC, 12));

        signOutLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new SignOutHandler().signOut();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                signOutLink.setText("<html><u>Sign Out -></u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                signOutLink.setText("<html>Sign Out -></html>");
            }
        });

        return signOutLink;
    }
}
