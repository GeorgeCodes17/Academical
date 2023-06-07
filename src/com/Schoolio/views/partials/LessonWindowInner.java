package com.Schoolio.views.partials;

import com.Schoolio.Launcher;
import com.Schoolio.api.LessonApi;
import com.Schoolio.exceptions.LessonException;
import com.Schoolio.objects.LessonObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Level;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class LessonWindowInner extends JPanel {

    private final EmptyBorder inputsMargin = new EmptyBorder(0, 0, 16, 0);

    public LessonWindowInner() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(35, 0, 35, 0));
        setBackground(Color.WHITE);

        String[] lessonOptions = getLessonOptions();

        JComboBox<String> lessonSelector = new JComboBox<>(lessonOptions);
        lessonSelector.setMaximumSize(new Dimension(200, 50));
        lessonSelector.setBorder(inputsMargin);

        JLabel dayOfWeekText = new JLabel("Day of week");
        dayOfWeekText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        dayOfWeekText.setBorder(inputsMargin);
        Box dayOfWeekSelector = new DayOfWeekSelector();
        dayOfWeekSelector.setBorder(inputsMargin);

        JLabel startTimeText = new JLabel("Start time");
        startTimeText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        startTimeText.setBorder(inputsMargin);
        JPanel startTimeInput = new TimeSelector(8, 45);
        startTimeInput.setBorder(inputsMargin);

        JLabel endTimeText = new JLabel("End time");
        endTimeText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        endTimeText.setBorder(inputsMargin);
        JPanel endTimeInput = new TimeSelector(9, 40);
        endTimeInput.setBorder(inputsMargin);

        JButton submitBtn = new JButton("Submit");
        submitBtn.setAlignmentX(JButton.CENTER_ALIGNMENT);

        add(lessonSelector);
        add(dayOfWeekText);
        add(dayOfWeekSelector);
        add(startTimeText);
        add(startTimeInput);
        add(endTimeText);
        add(endTimeInput);
        add(submitBtn);
        revalidate();
    }

    public String[] getLessonOptions() {
        LessonApi lessonApi = new LessonApi();
        String[] lessonOptions = new String[0];
        try {
            LessonObject[] lessonObjects = lessonApi.index();
            for (LessonObject lessonObject : lessonObjects) {
                String lessonOptionName = String.format("Year %s %s", lessonObject.getYear().getName(), lessonObject.getName());
                lessonOptions = ArrayUtils.add(lessonOptions, lessonOptionName);
            }
        } catch (LessonException | IOException e) {
            Launcher.logAll(Level.INFO, new LessonException("Failed to get 'Add Lesson' form fields data: " + e));
        }
        return lessonOptions;
    }
}
