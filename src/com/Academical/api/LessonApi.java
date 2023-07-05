package com.Academical.api;

import com.Academical.exceptions.LessonException;
import com.Academical.helpers.ConfigFile;
import com.Academical.helpers.GsonMultipleTimeFormats;
import com.Academical.objects.LessonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;

public class LessonApi {
    private final ConfigFile configFile = new ConfigFile();
    private final String apiUrl = configFile.config().getProperty("ACADEMICAL_API_URL");

    public LessonObject[] index() throws LessonException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(apiUrl + "lesson");
        request.addHeader("Accept", "application/json");

        HttpResponse response = client.execute(request);
        String responseContent = EntityUtils.toString(response.getEntity());
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new LessonException(responseContent);
        }
        return processTimetableData(responseContent);
    }

    private LessonObject[] processTimetableData(String data) {
        Type type = new TypeToken<LessonObject[]>() {}.getType();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Time.class, new GsonMultipleTimeFormats.TimeDeserializer())
                .registerTypeAdapter(Timestamp.class, new GsonMultipleTimeFormats.TimestampDeserializer())
                .create();
        return gson.fromJson(data, type);

    }
}
