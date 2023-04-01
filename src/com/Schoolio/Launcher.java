package com.Schoolio;

import com.Schoolio.views.MainWindow;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().show();
        });
    }
}
