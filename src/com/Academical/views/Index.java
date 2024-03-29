package com.Academical.views;

import com.Academical.Launcher;
import com.Academical.helpers.LoginHandler;
import com.Academical.helpers.SignOutHandler;
import com.Academical.views.account.LoginForm;
import com.Academical.views.partials.helpers.Colors;
import com.Academical.views.windows.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Index {
    private final JLabel signOutLink = new JLabel("<html>Sign Out -></html>");
    private boolean isFinalWindow = false;

    public Index(boolean refreshAuth) {
        if (refreshAuth) {
            Launcher.USER = new LoginHandler().authenticate();
        }
    }

    public Index(boolean refreshAuth, boolean isFinalWindow) {
        this.isFinalWindow = isFinalWindow;
        if (refreshAuth) {
            Launcher.USER = new LoginHandler().authenticate();
        }
    }

    public JPanel getHeaderLabel() {
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(Colors.MIDDLE_BLUE);

        JLabel heading = new JLabel("Academical", SwingConstants.CENTER);
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
        JLabel footer = new JLabel("Academical is a company, established in 2022, created by a developer who couldn't think of a good project to do", SwingConstants.CENTER);
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
                Launcher.USER = new SignOutHandler().signOut();
                if (!isFinalWindow) {
                    closeParentJFrame(signOutLink);
                }
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

    private void closeParentJFrame(JLabel label) {
        Container parent = label.getParent();
        while (parent != null) {
            if (parent instanceof JFrame frame) {
                frame.dispose();
                break;
            }
            parent = parent.getParent();
        }
    }
}
