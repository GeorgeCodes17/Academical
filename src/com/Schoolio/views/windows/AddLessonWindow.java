package com.Schoolio.views.windows;

import com.Schoolio.views.Index;

import javax.swing.*;
import java.awt.*;

public class AddLessonWindow extends JFrame {
    private final Index index = new Index(false);

    public AddLessonWindow() {
        JFrame addLessonFrame = new JFrame();
        addLessonFrame.setSize(600, 400);
        addLessonFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addLessonFrame.setTitle("Add Lesson");
        addLessonFrame.setVisible(true);

        addLessonFrame.add(index.getHeaderLabel(), BorderLayout.PAGE_START);
        addLessonFrame.add(index.getFooterLabel(), BorderLayout.PAGE_END);
    }
}
