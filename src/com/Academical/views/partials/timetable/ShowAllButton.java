package com.Academical.views.partials.timetable;

import com.Academical.views.windows.ShowLessonsWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowAllButton extends JLabel {
    private final String defaultText = "<html>Show All -></html>";
    private final String hoverText = "<html><u>Show All -></u></html>";

    private static ShowLessonsWindow showLessonsWindow;

    public ShowAllButton() {
        setBorder(new EmptyBorder(15, 0, 0, 0));
        setText(defaultText);
        setHorizontalAlignment(SwingConstants.CENTER);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(new Font("Roboto", Font.BOLD | Font.ITALIC, 12));
        setPreferredSize(new Dimension(150, 15));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showLessonsWindow = new ShowLessonsWindow();
                showLessonsWindow.addKeyListener(new EscapeKeyListener());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setText(hoverText);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setText(defaultText);
            }
        });
    }

    private static class EscapeKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                showLessonsWindow.dispose();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }
}
