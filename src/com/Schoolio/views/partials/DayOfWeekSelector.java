package com.Schoolio.views.partials;

import com.Schoolio.views.components.DayOfWeekRadio;

import javax.swing.*;

public class DayOfWeekSelector extends Box {
    public ButtonGroup buttonGroup = new ButtonGroup();

    private final JRadioButton mondayRadio = new DayOfWeekRadio("Mon", true);
    private final JRadioButton tuesdayRadio = new DayOfWeekRadio("Tue");
    private final JRadioButton wednesdayRadio = new DayOfWeekRadio("Wed");
    private final JRadioButton thursdayRadio = new DayOfWeekRadio("Thu");
    private final JRadioButton fridayRadio = new DayOfWeekRadio("Fri");
    private final JRadioButton saturdayRadio = new DayOfWeekRadio("Sat");
    private final JRadioButton sundayRadio = new DayOfWeekRadio("Sun");

    public DayOfWeekSelector() {
        super(BoxLayout.X_AXIS);

        buttonGroup.add(mondayRadio);
        buttonGroup.add(tuesdayRadio);
        buttonGroup.add(wednesdayRadio);
        buttonGroup.add(thursdayRadio);
        buttonGroup.add(fridayRadio);
        buttonGroup.add(saturdayRadio);
        buttonGroup.add(sundayRadio);

        add(mondayRadio);
        add(tuesdayRadio);
        add(wednesdayRadio);
        add(thursdayRadio);
        add(fridayRadio);
        add(saturdayRadio);
        add(sundayRadio);
    }
}
