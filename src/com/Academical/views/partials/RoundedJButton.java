package com.Academical.views.partials;

import com.Academical.views.partials.helpers.RoundedButtonUi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedJButton extends JButton {
    public RoundedJButton(String text, int width, int height, Color backgroundColor, Color foregroundColor) {
        super(text);
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setOpaque(true);
        setPreferredSize(new Dimension(width, height));
        setUI(new RoundedButtonUi());

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(darkenedColor(backgroundColor));
            }
            public void mouseExited(MouseEvent e) {
                setBackground(backgroundColor);
            }
        });
    }

    public RoundedJButton(Color backgroundColor, Color foregroundColor) {
        super();
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setOpaque(true);
        setUI(new RoundedButtonUi());

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(darkenedColor(backgroundColor));
            }
            public void mouseExited(MouseEvent e) {
                setBackground(backgroundColor);
            }
        });
    }

    private Color darkenedColor(Color backgroundColor) {
        int darkenedRedVal = (int) (backgroundColor.getRed() * 0.75);
        int darkenedGreenVal = (int) (backgroundColor.getGreen() * 0.75);
        int darkenedBlueVal = (int) (backgroundColor.getBlue() * 0.75);
        return new Color(darkenedRedVal, darkenedGreenVal, darkenedBlueVal);
    }
}
