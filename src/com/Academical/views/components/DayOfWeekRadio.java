package com.Academical.views.components;

import com.Academical.views.windows.MainWindow;

import javax.swing.*;

public class DayOfWeekRadio extends JRadioButton {
    public DayOfWeekRadio(String day) {
        super(day);
        setFont(MainWindow.MAIN_FONT);
    }

    public DayOfWeekRadio(String day, Boolean isDefault) {
        super(day, isDefault);
        setFont(MainWindow.MAIN_FONT);
    }
}
