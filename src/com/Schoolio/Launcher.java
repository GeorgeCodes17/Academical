package com.Schoolio;

import com.Schoolio.api.auth.AuthenticateApi;
import com.Schoolio.exceptions.GetUserInfoException;
import com.Schoolio.helpers.ConfigFile;
import com.Schoolio.objects.User;
import com.Schoolio.views.MainWindow;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.IOException;

public class Launcher {

    private static final String DEBUG_MODE = new ConfigFile().config().getProperty("CONSOLE_DEBUG");

    private static final Logger LOGGER_ALL_LEVELS = LogManager.getRootLogger();
    private static final Logger LOGGER_DEBUG = LogManager.getLogger("DebugLogger");
    private static final Logger LOGGER = LogManager.getLogger("ConsoleLogger");

    public static User USER;
    static {
        try {
            USER = new AuthenticateApi().getUserInfo();
        } catch (GetUserInfoException | IOException e) {
            Launcher.logAll(Level.ERROR, e);
            USER = new User(false);
        }
    }

    public static void main(String[] args) {
        if (!DEBUG_MODE.equals("on")) {
            Configurator.setLevel("ConsoleLogger", Level.OFF);
        }

        new MainWindow().show();
    }

    public static void logAll(Level level, Exception message) {
        LOGGER_ALL_LEVELS.log(level, message);
        LOGGER_DEBUG.log(level, message);
        LOGGER.log(level, message);
    }
}
