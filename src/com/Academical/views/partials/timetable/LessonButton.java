package com.Academical.views.partials.timetable;

import com.Academical.views.partials.RoundedJButton;
import com.Academical.views.partials.helpers.Colors;
import com.Academical.views.windows.AddLessonWindow;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class LessonButton extends RoundedJButton {
    private static AddLessonWindow addLessonWindow;

    public LessonButton() {
        super(Colors.AIR_SUPERIORITY_BLUE, Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addLessonWindow = new AddLessonWindow();
                addLessonWindow.addKeyListener(new EscapeKeyListener());
            }
        });

        final URL addIcon = Main.class.getClassLoader().getResource("plus-20.png");
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Image iconImage = toolkit.getImage(addIcon);
        setIcon(new ImageIcon(iconImage));
    }

    private static class EscapeKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                addLessonWindow.dispose();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }
}
