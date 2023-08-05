package com.Academical.views.windows;

import com.Academical.views.Index;
import com.Academical.views.partials.LessonWindowInner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AddLessonWindow extends JFrame {
    private final Index index = new Index(false);

    public AddLessonWindow() {
        setFocusable(true);
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Add Lesson");
        setVisible(true);

        JPanel lessonWindowContainer = new JPanel();
        lessonWindowContainer.setBorder(new EmptyBorder(35, 0, 35, 0));
        lessonWindowContainer.setLayout(new GridBagLayout());

        JPanel lessonWindowBorder = new JPanel();
        lessonWindowBorder.setBorder(new LineBorder(Color.WHITE, 36, true));
        lessonWindowBorder.setLayout(new BoxLayout(lessonWindowBorder, BoxLayout.Y_AXIS));

        LessonWindowInner lessonWindowInner = new LessonWindowInner();

        lessonWindowBorder.add(lessonWindowInner);
        lessonWindowContainer.add(lessonWindowBorder);

        add(index.getHeaderLabel(), BorderLayout.PAGE_START);
        add(lessonWindowContainer, BorderLayout.CENTER);
        add(index.getFooterLabel(), BorderLayout.PAGE_END);
    }
}
