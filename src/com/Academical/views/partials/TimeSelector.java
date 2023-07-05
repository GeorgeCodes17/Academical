package com.Academical.views.partials;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;

public class TimeSelector extends JPanel {
    private final JSpinner hourSpinner = new JSpinner();
    private final JSpinner minuteSpinner = new JSpinner();

    public TimeSelector(int defaultHour, int defaultMinute) {
        setLayout(new FlowLayout());
        createJSpinner(defaultHour, defaultMinute);
    }

    public void createJSpinner(int defaultHour, int defaultMinute) {
        SpinnerNumberModel hourModel = new SpinnerNumberModel(defaultHour, 0, 23, 1);
        SpinnerNumberModel minuteModel = new SpinnerNumberModel(defaultMinute, 0, 59, 1);

        hourSpinner.setModel(hourModel);
        minuteSpinner.setModel(minuteModel);

        Dimension spinnerSize = new Dimension(50, 20);
        hourSpinner.setPreferredSize(spinnerSize);
        minuteSpinner.setPreferredSize(spinnerSize);

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(hourSpinner);
        hourSpinner.setEditor(editor);
        editor = new JSpinner.NumberEditor(minuteSpinner);
        minuteSpinner.setEditor(editor);

        setOpaque(false);
        add(new JLabel("Hour:"));
        add(hourSpinner);
        add(new JLabel("Minute:"));
        add(minuteSpinner);
    }

    public Time getSelection() {
        int hourValue = Integer.parseInt(hourSpinner.getValue().toString());
        String time = hourValue < 9 ? "0" : "";
        time += hourSpinner.getValue().toString() + ":" + minuteSpinner.getValue().toString() + ":00";
        return Time.valueOf(time);
    }
}
