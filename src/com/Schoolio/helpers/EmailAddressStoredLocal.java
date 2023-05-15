package com.Schoolio.helpers;

import com.Schoolio.Launcher;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class EmailAddressStoredLocal {
    private final ConfigFile configFile = new ConfigFile();
    private final String loginCachePath = configFile.config().getProperty("LOGIN_CACHE_PATH");

    public void storeNewEmail(String emailAddress) {
        try {
            FileWriter fileWriter = new FileWriter(loginCachePath);
            fileWriter.write(emailAddress);
            fileWriter.close();
        } catch (IOException e) {
            Launcher.logAll(Level.ERROR, e);
            throw new RuntimeException(e);
        }
    }

    public Optional<String> getEmailAddress() throws IOException {
        String emailAddress = null;
        try {
            File myObj = new File(loginCachePath);
            Scanner myReader = new Scanner(myObj);
            if (myReader.hasNext()) {
                emailAddress = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            Launcher.logAll(Level.TRACE, e);
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(emailAddress);
    }

    public void deleteEmailAddress() {
        File file = new File(loginCachePath);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            Launcher.logAll(Level.DEBUG, e);
            throw new RuntimeException(e);
        }
    }
}
