package com.school.views.partials;

import com.school.views.partials.helpers.Colors;
import com.school.views.partials.helpers.RoundedBorder;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class Timetable {

    static final Font HEADER_FONT = new Font("Roboto", Font.BOLD, 16);
    static final Font TIMETABLE_FONT = new Font("Roboto", Font.PLAIN, 14);

    public static JPanel getTimetable() {
        JPanel timetable = new JPanel();
        timetable.setPreferredSize(new Dimension(300, timetable.getPreferredSize().height));

        timetable.setLayout(new BoxLayout(timetable, BoxLayout.Y_AXIS));

        timetable.add(getTimetableBody());

        return timetable;
    }

    private static JPanel getTimetableBody() {
        JPanel timetableBody = new JPanel(new GridLayout(7, 4));
        timetableBody.setSize(300, timetableBody.getPreferredSize().height);
        timetableBody.setBorder(new RoundedBorder(15, Colors.darkLava));

        timetableBody.add(timetableItem("Activity", true, true));
        timetableBody.add(timetableItem("Year", true, true));
        timetableBody.add(timetableItem("Start time", true, true));

        timetableBody.add(timetableItem("Trig", false, true));
        timetableBody.add(timetableItem("9", false, true));
        timetableBody.add(timetableItem("10:00", false, true));

        timetableBody.add(timetableItem("Algebra", false, true));
        timetableBody.add(timetableItem("9", false, true));
        timetableBody.add(timetableItem("11:00", false, true));

        timetableBody.add(timetableItem("Math", false, true));
        timetableBody.add(timetableItem("10", false, true));
        timetableBody.add(timetableItem("12:00", false, true));

        timetableBody.add(timetableItem("Trig", false, true));
        timetableBody.add(timetableItem("11", false, true));
        timetableBody.add(timetableItem("15:00", false, true));

        timetableBody.add(timetableItem("Form", false, true));
        timetableBody.add(timetableItem("11", false, true));
        timetableBody.add(timetableItem("13:00", false, true));

        timetableBody.add(timetableItem("Angles", false, false));
        timetableBody.add(timetableItem("7", false, false));
        timetableBody.add(timetableItem("13:35", false, false));

        return timetableBody;
    }

    private static JLabel timetableItem(String itemText, boolean bold, boolean bottomBorder) {
        JLabel item = new JLabel(itemText, SwingConstants.CENTER);
        item.setFont(bold ? HEADER_FONT : TIMETABLE_FONT);
        if (bottomBorder) {
            item.setBorder(new MatteBorder(0, 0, 1, 0, Colors.darkLava));
        }
        item.setPreferredSize(new Dimension(100, item.getPreferredSize().height));

        return item;
    }
}
