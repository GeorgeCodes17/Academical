package com.Schoolio;

import com.Schoolio.helpers.ConfigFile;
import com.Schoolio.views.MainWindow;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class Launcher {

    private static final String DEBUG_MODE = new ConfigFile().config().getProperty("DEBUG_MODE");

    private static final Logger LOGGER = LogManager.getLogger("FileLogger");

    public static void main(String[] args) {
        if (DEBUG_MODE.equals("on")) {
            Configurator.setLevel("FileLogger", Level.TRACE);
        } else {
            Configurator.setLevel("FileLogger", Level.OFF);
        }

        new MainWindow().show();
    }
}
