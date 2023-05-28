package com.Schoolio.views.partials;

import javax.swing.*;

public class DayOfWeekSelector extends Box {
    private final JRadioButton mondayRadio = new JRadioButton("Mon");
    private final JRadioButton tuesdayRadio = new JRadioButton("Tue");
    private final JRadioButton wednesdayRadio = new JRadioButton("Wed");
    private final JRadioButton thursdayRadio = new JRadioButton("Thu");
    private final JRadioButton fridayRadio = new JRadioButton("Fri");
    private final JRadioButton saturdayRadio = new JRadioButton("Sat");
    private final JRadioButton sundayRadio = new JRadioButton("Sun");

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
