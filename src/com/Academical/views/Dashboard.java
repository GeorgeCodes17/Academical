package com.Academical.views;

import com.Academical.views.components.StatusBoard;
import com.Academical.views.partials.TeamsButtonPanel;
import com.Academical.views.partials.Timetable;
import com.Academical.views.partials.timetable.ShowAllButton;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JPanel {

    public Dashboard() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(45, 55, 20, 55));

        add(new StatusBoard(), BorderLayout.LINE_START);
        add(new TeamsButtonPanel(), BorderLayout.CENTER);

        Timetable timetable = new Timetable(false);
        timetable.add(new ShowAllButton(), "align center, dock south");
        add(timetable, BorderLayout.LINE_END);
    }
}
