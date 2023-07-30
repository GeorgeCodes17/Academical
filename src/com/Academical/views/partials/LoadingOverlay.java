package com.Academical.views.partials;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class LoadingOverlay extends JPanel {
    public LoadingOverlay(Dimension containerSize) {
        URL url = Main.class.getClassLoader().getResource("gifs/loading-spinner.gif");
        ImageIcon spinnerImage = new ImageIcon(url);
        JLabel loadingSpinner = new JLabel(spinnerImage);
        loadingSpinner.setBounds(containerSize.width-100, containerSize.height-100, 200, 200);

        add(loadingSpinner);
    }
}
