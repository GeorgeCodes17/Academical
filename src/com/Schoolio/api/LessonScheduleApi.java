package com.Schoolio.api;

import com.Schoolio.Launcher;
import com.Schoolio.exceptions.GetLessonScheduleException;
import com.Schoolio.helpers.BearerToken;
import com.Schoolio.helpers.ConfigFile;
import com.Schoolio.helpers.GsonMultipleTimeFormats;
import com.Schoolio.objects.BearerObject;
import com.Schoolio.objects.IdTokenObject;
import com.Schoolio.objects.LessonScheduleObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

public class LessonScheduleApi {
    private final ConfigFile configFile = new ConfigFile();
    private final String apiUrl = configFile.config().getProperty("API_URL");
    private final BearerObject bearer;
    private final IdTokenObject idTokenObject;

    public LessonScheduleApi() {
        Optional<String> bearerRaw = new BearerToken().getBearerToken();
        bearer = bearerRaw.map(BearerObject::new).orElseGet(BearerObject::new);
        idTokenObject = new IdTokenObject(bearer);
    }

    public LessonScheduleObject[] index() throws GetLessonScheduleException{
        HttpClient client = HttpClientBuilder.create().build();

        HttpGet request = new HttpGet(apiUrl + "/secured/lesson-schedule");
        request.addHeader("Authorization", bearer.getAccessToken());
        request.addHeader("SubId", idTokenObject.getSub());

        HttpResponse response;
        try {
            response = client.execute(request);
            String responseContent = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() != 200) {
                Launcher.logAll(Level.INFO, new GetLessonScheduleException("Failed to get lesson schedule at: " + responseContent));
                throw new GetLessonScheduleException(responseContent);
            }
            return processTimetableData(responseContent);
        } catch (IOException e) {
            Launcher.logAll(Level.FATAL, e);
            throw new GetLessonScheduleException(e);
        }
    }

    private LessonScheduleObject[] processTimetableData(String data) {
        Type type = new TypeToken<LessonScheduleObject[]>() {}.getType();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Time.class, new GsonMultipleTimeFormats.TimeDeserializer())
                .registerTypeAdapter(Timestamp.class, new GsonMultipleTimeFormats.TimestampDeserializer())
                .create();
        return gson.fromJson(data, type);

    }
}
