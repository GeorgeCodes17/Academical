package com.Schoolio.views.partials;

import com.Schoolio.views.partials.helpers.Colors;
import com.Schoolio.views.windows.LessonWindow;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class LessonButton extends RoundedJButton{
    public LessonButton() {
        super(Colors.AIR_SUPERIORITY_BLUE, Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new LessonWindow();
            }
        });

        final URL addIcon = Main.class.getClassLoader().getResource("plus-20.png");
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Image iconImage = toolkit.getImage(addIcon);
        setIcon(new ImageIcon(iconImage));
    }
}