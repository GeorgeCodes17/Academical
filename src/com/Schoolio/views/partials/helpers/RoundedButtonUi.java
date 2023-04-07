package com.Schoolio.views.partials.helpers;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

public class RoundedButtonUi extends BasicButtonUI {

    private static RoundedButtonUi button;

    public static ComponentUI createUI(JComponent c) {
        if (button != null) {
            return button;
        }
        button = new RoundedButtonUi();
        return button;
    }

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setOpaque(false);
    }

    @Override
    public void paint(Graphics graphics, JComponent component) {
        Graphics2D g2d = (Graphics2D) graphics.create();
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(hints);
        g2d.setColor(component.getBackground());
        g2d.fill(new RoundRectangle2D.Double(0, 0, component.getWidth() - 1, component.getHeight() - 1, 15, 15));
        g2d.setColor(component.getForeground());
        super.paint(graphics, component);
        g2d.dispose();
    }

}
