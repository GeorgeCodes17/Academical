package com.school.views.partials.helpers;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

public class RoundedButton extends BasicButtonUI {

    private static RoundedButton button;

    public static ComponentUI createUI(JComponent c) {
        if (button != null) {
            return button;
        }
        button = new RoundedButton();
        return button;
    }

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setOpaque(false);
        b.setBackground(Color.ORANGE);
        b.setForeground(Color.black);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g.create();
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(hints);
        g2d.setColor(c.getBackground());
        g2d.fill(new RoundRectangle2D.Double(0, 0, c.getWidth() - 1, c.getHeight() - 1, 15, 15));
        g2d.setColor(c.getForeground());
        super.paint(g, c);
        g2d.dispose();
    }

}
