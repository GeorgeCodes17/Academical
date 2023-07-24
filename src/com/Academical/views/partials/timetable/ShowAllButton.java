package com.Academical.views.partials.timetable;

import com.Academical.views.windows.ShowLessonsWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowAllButton extends JLabel {
    String defaultText = "<html>Show All -></html>";
    String hoverText = "<html><u>Show All -></u></html>";

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
                new ShowLessonsWindow();
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
}
