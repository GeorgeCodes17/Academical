package com.Academical.views.partials;

import com.Academical.Launcher;
import com.Academical.api.LessonApi;
import com.Academical.api.LessonScheduleApi;
import com.Academical.api.WeekOptionApi;
import com.Academical.enums.DayOfWeekEnum;
import com.Academical.enums.WeekOptionEnum;
import com.Academical.exceptions.GetWeekOptionException;
import com.Academical.exceptions.LessonException;
import com.Academical.exceptions.LessonScheduleException;
import com.Academical.helpers.BearerToken;
import com.Academical.objects.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Level;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

public class LessonWindowInner extends JPanel {

    private final EmptyBorder inputsMargin = new EmptyBorder(0, 0, 16, 0);
    private final DayOfWeekSelector dayOfWeekSelector;
    private final LessonScheduleApi lessonScheduleApi = new LessonScheduleApi();

    private WeekOptionEnum currentWeekOption;
    {
        try {
            currentWeekOption = new WeekOptionApi().index();
        } catch (GetWeekOptionException | IOException e) {
            Launcher.logAll(Level.WARN, e);
        }
    }
    private final BearerObject bearer;
    private final IdTokenObject idTokenObject;

    {
        Optional<String> bearerRaw = new BearerToken().getBearerToken();
        bearer = bearerRaw.map(BearerObject::new).orElseGet(BearerObject::new);
        idTokenObject = new IdTokenObject(bearer);
    }

    public LessonWindowInner() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(35, 0, 35, 0));
        setBackground(Color.WHITE);

        JLabel weekOptionsText = new JLabel("Week Option (defaults to current week)");
        weekOptionsText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        weekOptionsText.setBorder(inputsMargin);
        WeekOptionEnum[] weekOptions = new WeekOptionEnum[] {WeekOptionEnum.A, WeekOptionEnum.B};
        JComboBox<WeekOptionEnum> weekOptionSelector = new JComboBox<>(weekOptions);
        weekOptionSelector.setSelectedItem(currentWeekOption);
        weekOptionSelector.setMinimumSize(new Dimension(weekOptionSelector.getMinimumSize().width, 50));
        weekOptionSelector.setBorder(inputsMargin);

        DefaultComboBoxModel<LessonItem> lessonOptions = getLessonOptions();
        JComboBox<LessonItem> lessonSelector = new JComboBox<>(lessonOptions);
        lessonSelector.setMinimumSize(new Dimension(lessonSelector.getMinimumSize().width, 50));
        lessonSelector.setBorder(inputsMargin);

        JLabel dayOfWeekText = new JLabel("Day of week");
        dayOfWeekText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        dayOfWeekText.setBorder(inputsMargin);
        dayOfWeekSelector = new DayOfWeekSelector();
        dayOfWeekSelector.setBorder(inputsMargin);

        JLabel startTimeText = new JLabel("Start time");
        startTimeText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        startTimeText.setBorder(inputsMargin);
        TimeSelector startTimeInput = new TimeSelector(8, 45);
        startTimeInput.setBorder(inputsMargin);

        JLabel endTimeText = new JLabel("End time");
        endTimeText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        endTimeText.setBorder(inputsMargin);
        TimeSelector endTimeInput = new TimeSelector(9, 40);
        endTimeInput.setBorder(inputsMargin);

        JButton submitBtn = new JButton("Submit");
        submitBtn.setAlignmentX(JButton.CENTER_ALIGNMENT);

        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LessonItem lessonSelected = (LessonItem) lessonSelector.getSelectedItem();
                DayOfWeekEnum dayOfWeekEnum = getDayOfWeek();

                LessonScheduleObject lessonToAdd = new LessonScheduleObject(
                        lessonSelected.lessonObject(),
                        (WeekOptionEnum) weekOptionSelector.getSelectedItem(),
                        dayOfWeekEnum,
                        startTimeInput.getSelection(),
                        endTimeInput.getSelection()
                );

                try {
                    lessonScheduleApi.store(idTokenObject.getSub(), lessonToAdd);
                } catch (LessonScheduleException | UnsupportedEncodingException ex) {
                    try {
                        throw new LessonScheduleException(ex);
                    } catch (LessonScheduleException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            }
        });

        add(dayOfWeekText);
        add(weekOptionsText);
        add(weekOptionSelector);
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

    private DefaultComboBoxModel<LessonItem> getLessonOptions() {
        LessonApi lessonApi = new LessonApi();
        LessonItem[] lessonOptions = new LessonItem[0];
        try {
            LessonObject[] lessonObjects = lessonApi.index();
            for (LessonObject lessonObject : lessonObjects) {
                String lessonOptionName = String.format("Year %s %s", lessonObject.getYear().getName(), lessonObject.getName());
                LessonItem lessonItem = new LessonItem(lessonOptionName, lessonObject);
                lessonOptions = ArrayUtils.add(lessonOptions, lessonItem);
            }
        } catch (LessonException | IOException e) {
            Launcher.logAll(Level.INFO, new LessonException("Failed to get 'Add Lesson' form fields data: " + e));
        }
        return new DefaultComboBoxModel<>(lessonOptions);
    }

    private DayOfWeekEnum getDayOfWeek() {
        String dayOfWeek = dayOfWeekSelector.buttonGroup.getSelection().toString();
        return DayOfWeekEnum.fromString(dayOfWeek);
    }
}
