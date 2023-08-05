package com.Academical.views.windows;

import com.Academical.views.Index;
import com.Academical.views.partials.Timetable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ShowLessonsWindow extends JFrame {
    private final Index index = new Index(true);

    public ShowLessonsWindow() {
        setSize(1450, 600);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Show All Lessons");
        setVisible(true);

        add(index.getHeaderLabel(), BorderLayout.PAGE_START);
        Timetable timetable = new Timetable(true);
        timetable.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(timetable, BorderLayout.CENTER);
        add(index.getFooterLabel(), BorderLayout.PAGE_END);
    }
}
