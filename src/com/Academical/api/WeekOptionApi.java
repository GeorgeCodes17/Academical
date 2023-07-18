package com.Academical.api;

import com.Academical.Launcher;
import com.Academical.enums.WeekOptionEnum;
import com.Academical.exceptions.GetWeekOptionException;
import com.Academical.helpers.ConfigFile;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Level;

import java.io.IOException;

public class WeekOptionApi {
    private final String apiUrl = new ConfigFile().config().getProperty("ACADEMICAL_API_URL");

    public WeekOptionEnum index() throws GetWeekOptionException, IOException {
        HttpClient client = HttpClientBuilder.create().build();

        HttpGet request = new HttpGet(apiUrl + "week-option");
        request.addHeader("Accept", "application/json");

        String responseContent;
        try {
            HttpResponse response = client.execute(request);
            responseContent = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() != 200) {
                GetWeekOptionException getWeekOptionException = new GetWeekOptionException("Failed to get current week option");
                Launcher.logAll(Level.WARN, getWeekOptionException);
                throw getWeekOptionException;
            }
            System.out.println(responseContent);
        } catch (IOException e) {
            Launcher.logAll(Level.FATAL, e);
            throw e;
        }
        return WeekOptionEnum.fromString(responseContent);
    }
}
