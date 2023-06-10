package com.Schoolio.views.components;

import com.Schoolio.views.windows.MainWindow;

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
