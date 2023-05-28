package com.Schoolio.views.partials;

import com.Schoolio.views.components.DayOfWeekRadio;

import javax.swing.*;

public class DayOfWeekSelector extends Box {
    private final JRadioButton mondayRadio = new DayOfWeekRadio("Mon");
    private final JRadioButton tuesdayRadio = new DayOfWeekRadio("Tue");
    private final JRadioButton wednesdayRadio = new DayOfWeekRadio("Wed");
    private final JRadioButton thursdayRadio = new DayOfWeekRadio("Thu");
    private final JRadioButton fridayRadio = new DayOfWeekRadio("Fri");
    private final JRadioButton saturdayRadio = new DayOfWeekRadio("Sat");
    private final JRadioButton sundayRadio = new DayOfWeekRadio("Sun");

    public DayOfWeekSelector() {
        super(BoxLayout.X_AXIS);

        ButtonGroup bg = new ButtonGroup();
        bg.add(mondayRadio);
        bg.add(tuesdayRadio);
        bg.add(wednesdayRadio);
        bg.add(thursdayRadio);
        bg.add(fridayRadio);
        bg.add(saturdayRadio);
        bg.add(sundayRadio);

        add(mondayRadio);
        add(tuesdayRadio);
        add(wednesdayRadio);
        add(thursdayRadio);
        add(fridayRadio);
        add(saturdayRadio);
        add(sundayRadio);
    }
}
