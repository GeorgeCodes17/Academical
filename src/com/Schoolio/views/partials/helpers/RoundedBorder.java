package com.Schoolio.views.partials.helpers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.Border;

public class RoundedBorder implements Border {

    private final int RADIUS;
    private final Color COLOR;

    public RoundedBorder(int radius, Color color) {
        this.RADIUS = radius;
        this.COLOR = color;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(RADIUS, RADIUS, RADIUS, RADIUS);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(COLOR);
        g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, RADIUS, RADIUS));
        g2d.dispose();
    }
}
