package com.Schoolio.views.windows;

import com.Schoolio.Launcher;
import com.Schoolio.views.Dashboard;
import com.Schoolio.views.Index;
import com.Schoolio.views.account.LoginForm;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainWindow {
    public static final Font MAIN_FONT = new Font("Roboto", Font.PLAIN, 14);
    public static final JFrame WINDOW = new JFrame();

    public MainWindow() {
        setFonts();
        addAppIcon();

//        WINDOW.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        WINDOW.setVisible(true);
        WINDOW.setTitle("Schoolio");
        WINDOW.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        WINDOW.setSize(800, 500);
        WINDOW.getContentPane().setBackground(Color.white);

        Index index = new Index(false, true);

        WINDOW.add(index.getHeaderLabel(), BorderLayout.PAGE_START);
        if(Launcher.USER.signedIn()) {
            WINDOW.add(new Dashboard(), BorderLayout.CENTER);
        } else {
            WINDOW.add(new LoginForm(), BorderLayout.CENTER);
        }

        WINDOW.add(index.getFooterLabel(), BorderLayout.PAGE_END);
    }

    private void addAppIcon() {
        String APP_ICON_PATH = "classroom-icon.png";
        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        final URL imageResource = Main.class.getClassLoader().getResource(APP_ICON_PATH);
        final Image image = defaultToolkit.getImage(imageResource);
        final Taskbar taskbar = Taskbar.getTaskbar();
        taskbar.setIconImage(image);
    }

    private void setFonts() {
        UIManager.put("Label.font", MAIN_FONT);
        UIManager.put("TextField.font", MAIN_FONT);
        UIManager.put("PasswordField.font", MAIN_FONT);
        UIManager.put("Button.font", MAIN_FONT);
    }

    public void show() {
        WINDOW.setVisible(true);
    }
}