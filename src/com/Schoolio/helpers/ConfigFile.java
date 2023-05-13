package com.Schoolio.helpers;

import com.Schoolio.Launcher;
import org.apache.logging.log4j.Level;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigFile {
    public Properties config() {
        try {
            FileInputStream configFile = new FileInputStream("config.properties");
            Properties getConfigFile = new Properties();
            getConfigFile.load(configFile);
            return getConfigFile;
        } catch (Exception e) {
            Launcher.logAll(Level.FATAL, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
