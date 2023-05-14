package com.Schoolio.views.partials;

import com.Schoolio.api.LessonScheduleApi;
import com.Schoolio.exceptions.GetLessonScheduleException;
import com.Schoolio.objects.LessonScheduleObject;
import com.Schoolio.views.partials.helpers.Colors;
import com.Schoolio.views.partials.helpers.RoundedBorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;

public class Timetable {
    private final Font headerFont = new Font("Roboto", Font.BOLD, 16);
    private final Font timetableFont = new Font("Roboto", Font.PLAIN, 14);
    private final LessonScheduleApi lessonScheduleApi = new LessonScheduleApi();

    public JPanel getTimetable() {
        JPanel timetable = new JPanel();
        timetable.setPreferredSize(new Dimension(300, timetable.getPreferredSize().height));

        timetable.setLayout(new BoxLayout(timetable, BoxLayout.Y_AXIS));

        LessonScheduleObject[] lessonSchedule;
        try {
            lessonSchedule = lessonScheduleApi.index();
        } catch (GetLessonScheduleException e) {
            // TODO Do something visually when error occurs
            throw new RuntimeException(e);
        }
        timetable.add(getTimetableBody(lessonSchedule));

        return timetable;
    }

    private JPanel getTimetableBody(LessonScheduleObject[] lessonSchedule) {
        JPanel timetableBody = new JPanel();
        timetableBody.setBorder(new RoundedBorder(15, Colors.DARK_LAVA));

        if (lessonSchedule == null) {
            JLabel noLessonsLabel = new JLabel("No lessons scheduled");
            noLessonsLabel.setBorder(new EmptyBorder(25,0,0,0));

            timetableBody.add(noLessonsLabel);
        } else {
            timetableBody.setLayout(new GridLayout(lessonSchedule.length + 1, 3));

            timetableBody.add(timetableEvent("Activity", true, true));
            timetableBody.add(timetableEvent("Year", true, true));
            timetableBody.add(timetableEvent("Start time", true, true));

            Iterator<LessonScheduleObject> lessonScheduleIterator = Arrays.stream(lessonSchedule).iterator();
            while (lessonScheduleIterator.hasNext()) {
                LessonScheduleObject currentLesson = lessonScheduleIterator.next();

                boolean bottomBorder = lessonScheduleIterator.hasNext();
                timetableBody.add(timetableEvent(currentLesson.getLesson().getName(), false, bottomBorder));
                timetableBody.add(timetableEvent(currentLesson.getYear().getName(), false, bottomBorder));
                timetableBody.add(timetableEvent(currentLesson.getStart().toString(), false, bottomBorder));
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
