package com.Schoolio.views.windows;

import com.Schoolio.views.Index;
import com.Schoolio.views.partials.LessonWindowInner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LessonWindow extends JFrame {
    private final Index index = new Index(false);

    public LessonWindow() {
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Add Lesson");
        setVisible(true);

        JPanel lessonWindowContainer = new JPanel();
        lessonWindowContainer.setBorder(new EmptyBorder(35, 100, 35, 100));
        lessonWindowContainer.setLayout(new GridBagLayout());

        JPanel lessonWindowInner = new LessonWindowInner();

        lessonWindowContainer.add(lessonWindowInner);

        add(index.getHeaderLabel(), BorderLayout.PAGE_START);
        add(lessonWindowContainer, BorderLayout.CENTER);
        add(index.getFooterLabel(), BorderLayout.PAGE_END);
    }
}
