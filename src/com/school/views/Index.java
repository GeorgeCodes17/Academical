package com.school.views;

import com.school.views.partials.helpers.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Index {
    public static JLabel getHeaderLabel() {
        JLabel heading = new JLabel("Schoolio", SwingConstants.CENTER);
        heading.setBackground(Colors.middleBlue);
        heading.setFont(new Font("Roboto", Font.PLAIN, 32));
        heading.setOpaque(true);
        heading.setBorder(new EmptyBorder(15,0,0,0));
        return heading;
    }

    public static JLabel getFooterLabel() {
        JLabel footer = new JLabel("Schoolio is a company, established in 2022, created by a developer who couldn't think of a good project to do", SwingConstants.CENTER);
        footer.setBackground(Colors.middleBlue);
        footer.setOpaque(true);
        footer.setBorder(new EmptyBorder(0,0,10, 0));
        return footer;
    }
}
