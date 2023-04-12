package com.Schoolio.helpers;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GsonMultipleTimeFormats {
    private final static DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private final static DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static class TimeDeserializer implements JsonDeserializer<Time> {
        public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                Date date = TIME_FORMAT.parse(json.getAsString());
                return new Time(date.getTime());
            } catch (ParseException e) {
                throw new JsonParseException("Unable to parse time", e);
            }
        }
    }

    public static class TimestampDeserializer implements JsonDeserializer<Timestamp> {
        public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                Date date = TIMESTAMP_FORMAT.parse(json.getAsString());
                return new Timestamp(date.getTime());
            } catch (ParseException e) {
                throw new JsonParseException("Unable to parse timestamp", e);
            }
        }
    }

}
