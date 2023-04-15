package com.Schoolio.helpers;

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
            e.printStackTrace();
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
            e.printStackTrace();
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
            throw new RuntimeException(e);
        }
    }
}
