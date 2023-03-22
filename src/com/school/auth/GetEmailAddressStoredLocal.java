package com.school.auth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class GetEmailAddressStoredLocal {
    static final String filePath = ".src/email-logged-in.txt";
    public static void storeNewEmail(String emailAddress) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(emailAddress);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Optional<String> getEmailAddress() throws IOException {
        String emailAddress = null;
        try {
            File myObj = new File(filePath);
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
}
