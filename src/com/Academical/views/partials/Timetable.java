package com.Academical.views.partials;

import com.Academical.Launcher;
import com.Academical.api.LessonScheduleApi;
import com.Academical.exceptions.LessonScheduleException;
import com.Academical.objects.LessonScheduleObject;
import com.Academical.views.partials.helpers.Colors;
import com.Academical.views.partials.helpers.RoundedBorder;
import com.Academical.views.partials.timetable.LessonButton;
import net.miginfocom.swing.MigLayout;
import org.apache.logging.log4j.Level;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;

public class Timetable extends JPanel {
    private final Font headerFont = new Font("Roboto", Font.BOLD, 16);
    private final Font timetableFont = new Font("Roboto", Font.PLAIN, 14);
    private final LessonScheduleApi lessonScheduleApi = new LessonScheduleApi();

    private final JLabel noLessonsLabel = new JLabel("No lessons scheduled");

    public Timetable(boolean showAllFields) {
        setPreferredSize(new Dimension(300, getPreferredSize().height));
        setLayout(new MigLayout("fill", "[100%]", "[15%][85%]"));

        LessonScheduleObject[] lessonSchedule = null;
        try {
            lessonSchedule = lessonScheduleApi.index();
        } catch (LessonScheduleException e) {
            Launcher.logAll(Level.WARN, e);
            noLessonsLabel.setText("Failed to retrieve lesson schedule");
            noLessonsLabel.setForeground(Color.RED);
        }

        add(new LessonButton(), "align right, wrap");
        add(getTimetableBody(lessonSchedule, showAllFields), "grow");
    }

    private JPanel getTimetableBody(LessonScheduleObject[] lessonSchedule, boolean showAllFields) {
        JPanel timetableBody = new JPanel();
        timetableBody.setBorder(new RoundedBorder(15, Colors.DARK_LAVA));

        if (lessonSchedule == null || lessonSchedule.length <= 1) {
            noLessonsLabel.setBorder(new EmptyBorder(40,0,0,0));

            timetableBody.add(noLessonsLabel);
        } else {
            timetableBody.setLayout(new GridLayout(lessonSchedule.length + 1, 3));

            if (showAllFields) {
                timetableBody.add(timetableEvent("Assigned By", true, true));
                timetableBody.add(timetableEvent("Lesson", true, true));
                timetableBody.add(timetableEvent("Week Option", true, true));
                timetableBody.add(timetableEvent("Day of Week", true, true));
                timetableBody.add(timetableEvent("Year", true, true));
                timetableBody.add(timetableEvent("Start time", true, true));
                timetableBody.add(timetableEvent("End time", true, true));
                timetableBody.add(timetableEvent("Updated at", true, true));
            } else {
                timetableBody.add(timetableEvent("Activity", true, true));
                timetableBody.add(timetableEvent("Year", true, true));
                timetableBody.add(timetableEvent("Start time", true, true));
            }

            Iterator<LessonScheduleObject> lessonScheduleIterator = Arrays.stream(lessonSchedule).iterator();
            while (lessonScheduleIterator.hasNext()) {
                LessonScheduleObject currentLesson = lessonScheduleIterator.next();
                boolean bottomBorder = lessonScheduleIterator.hasNext();

                if (showAllFields) {
                    timetableBody.add(timetableEvent(currentLesson.getAssignedBy(), false, bottomBorder));
                    timetableBody.add(timetableEvent(currentLesson.getLesson().getName(), false, bottomBorder));
                    timetableBody.add(timetableEvent(currentLesson.getWeekOption().toString(), false, bottomBorder));
                    timetableBody.add(timetableEvent(currentLesson.getDayOfWeek().toString(), false, bottomBorder));
                    timetableBody.add(timetableEvent(currentLesson.getLesson().getYear().getName(), false, bottomBorder));
                    timetableBody.add(timetableEvent(currentLesson.getStart().toString(), false, bottomBorder));
                    timetableBody.add(timetableEvent(currentLesson.getEnd().toString(), false, bottomBorder));
                    timetableBody.add(timetableEvent(currentLesson.getUpdatedAt().toString(), false, bottomBorder));
                } else {
                    timetableBody.add(timetableEvent(currentLesson.getLesson().getName(), false, bottomBorder));
                    timetableBody.add(timetableEvent(currentLesson.getLesson().getYear().getName(), false, bottomBorder));
                    timetableBody.add(timetableEvent(currentLesson.getStart().toString(), false, bottomBorder));
                }
            }
        }

        return timetableBody;
    }

    private JLabel timetableEvent(String itemText, boolean bold, boolean bottomBorder) {
        JLabel item = new JLabel(itemText, SwingConstants.CENTER);
        item.setFont(bold ? headerFont : timetableFont);
        if (bottomBorder) {
            item.setBorder(new MatteBorder(0, 0, 1, 0, Colors.DARK_LAVA));
        }
        item.setPreferredSize(new Dimension(100, item.getPreferredSize().height));

        return item;
    }
}
