package com.Schoolio.api;

import com.Schoolio.Launcher;
import com.Schoolio.exceptions.LessonScheduleException;
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
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    public LessonScheduleObject[] index() throws LessonScheduleException {
        HttpClient client = HttpClientBuilder.create().build();

        HttpGet request = new HttpGet(apiUrl + "secured/lesson-schedule/show");
        request.addHeader("Authorization", bearer.getAccessToken());
        request.addHeader("SubId", idTokenObject.getSub());

        HttpResponse response;
        try {
            response = client.execute(request);
            String responseContent = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() != 200) {
                Launcher.logAll(Level.INFO, new LessonScheduleException("Failed to get lesson schedule at: " + responseContent));
                throw new LessonScheduleException(responseContent);
            }
            return processTimetableData(responseContent);
        } catch (IOException e) {
            Launcher.logAll(Level.FATAL, e);
            throw new LessonScheduleException(e);
        }
    }

    public void store(String sub, LessonScheduleObject lessonSchedule)
            throws LessonScheduleException, UnsupportedEncodingException {
        HttpClient client = HttpClientBuilder.create().build();

        HttpPost request = new HttpPost(apiUrl + "secured/lesson-schedule");
        request.addHeader("Authorization", bearer.getAccessToken());
        request.addHeader("SubId", idTokenObject.getSub());
        UrlEncodedFormEntity params = getStoreParameters(lessonSchedule);
        request.setEntity(params);

        HttpResponse response;
        try {
            response = client.execute(request);
            String responseContent = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() != 200) {
                Launcher.logAll(Level.INFO, new LessonScheduleException("Failed to store lesson schedule at: " + responseContent));
                throw new LessonScheduleException(responseContent);
            }
        } catch (IOException e) {
            Launcher.logAll(Level.FATAL, e);
            throw new LessonScheduleException(e);
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

    private UrlEncodedFormEntity getStoreParameters(LessonScheduleObject lessonSchedule) throws UnsupportedEncodingException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("lesson", String.valueOf(lessonSchedule.getLesson().getId())));
        params.add(new BasicNameValuePair("assigned_by", idTokenObject.getEmail()));
        params.add(new BasicNameValuePair("day_of_week", lessonSchedule.getDayOfWeek().toString()));
        params.add(new BasicNameValuePair("start", lessonSchedule.getStart().toString()));
        params.add(new BasicNameValuePair("end", lessonSchedule.getEnd().toString()));

        return new UrlEncodedFormEntity(params);
    }
}
