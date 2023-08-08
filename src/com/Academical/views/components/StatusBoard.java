package com.Academical.views.components;

import com.Academical.Launcher;
import com.Academical.api.LessonScheduleApi;
import com.Academical.exceptions.LessonScheduleException;
import com.Academical.objects.LessonScheduleObject;
import org.apache.logging.log4j.Level;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;

public class StatusBoard extends JPanel {
    public StatusBoard() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, getPreferredSize().height));

        JLabel welcomeMessage = new JLabel("<html>Welcome, <b>" + Launcher.USER.name() + "</b></html>");
        welcomeMessage.setBorder(new EmptyBorder(0, 0, 30, 0));
        add(welcomeMessage);

        LessonScheduleObject firstLessonObject = null;
        try {
            firstLessonObject = new LessonScheduleApi().index()[0];
        } catch (LessonScheduleException e) {
            Launcher.logAll(Level.ERROR, e);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String firstLessonTime = simpleDateFormat.format(firstLessonObject.getStart());

        JLabel firstLessonMsg = new JLabel("<html>Your first lesson is " + "<b>"
                + firstLessonObject.getLesson().getName() +
                "</b> at <b>" + firstLessonTime + "</b></html>");
        add(firstLessonMsg);
    }
}
