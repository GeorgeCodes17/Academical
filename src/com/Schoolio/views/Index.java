package com.Schoolio.views;

import com.Schoolio.Launcher;
import com.Schoolio.api.auth.AuthenticateApi;
import com.Schoolio.exceptions.GetUserInfoException;
import com.Schoolio.helpers.SignOutHandler;
import com.Schoolio.objects.User;
import com.Schoolio.views.account.LoginForm;
import com.Schoolio.views.partials.helpers.Colors;
import com.Schoolio.views.windows.MainWindow;
import org.apache.logging.log4j.Level;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Index {
    private final JLabel signOutLink = new JLabel("<html>Sign Out -></html>");

    public Index(boolean withAuth) {
        if (withAuth) {
            try {
                Launcher.USER = new AuthenticateApi().getUserInfo();
            } catch (GetUserInfoException | IOException e) {
                Launcher.logAll(Level.ERROR, e);
                Launcher.USER = new User(false);
            }
        }
    }

    public JPanel getHeaderLabel() {
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(Colors.MIDDLE_BLUE);

        JLabel heading = new JLabel("Schoolio", SwingConstants.CENTER);
        heading.setBackground(Colors.MIDDLE_BLUE);
        heading.setFont(new Font("Roboto", Font.PLAIN, 32));
        heading.setBorder(new EmptyBorder(10,0,10,0));

        header.add(heading, BorderLayout.CENTER);
        if (Launcher.USER.loggedIn()) {
            JLabel spacer = new JLabel();
            spacer.setPreferredSize(new Dimension(150, 15));
            header.add(spacer, BorderLayout.LINE_START);
            header.add(getSignOutLink(), BorderLayout.LINE_END);
        }
        return header;
    }

    public JLabel getFooterLabel() {
        JLabel footer = new JLabel("Schoolio is a company, established in 2022, created by a developer who couldn't think of a good project to do", SwingConstants.CENTER);
        footer.setBackground(Colors.MIDDLE_BLUE);
        footer.setOpaque(true);
        footer.setBorder(new EmptyBorder(10,0,10, 0));
        return footer;
    }

    public JLabel getSignOutLink() {
        signOutLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signOutLink.setBorder(new EmptyBorder(0, 0, 0, 25));
        signOutLink.setFont(new Font("Roboto", Font.BOLD | Font.ITALIC, 12));
        signOutLink.setPreferredSize(new Dimension(150, 15));

        signOutLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new SignOutHandler().signOut();
                MainWindow.WINDOW.getContentPane().removeAll();
                MainWindow.WINDOW.add(getHeaderLabel(), BorderLayout.PAGE_START);
                MainWindow.WINDOW.add(new LoginForm(), BorderLayout.CENTER);
                MainWindow.WINDOW.add(getFooterLabel(), BorderLayout.PAGE_END);
                MainWindow.WINDOW.repaint();
                MainWindow.WINDOW.revalidate();
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
