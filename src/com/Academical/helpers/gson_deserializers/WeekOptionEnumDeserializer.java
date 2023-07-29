package com.Academical.helpers.gson_deserializers;

import com.Academical.enums.WeekOptionEnum;

import com.google.gson.*;

import java.lang.reflect.Type;

public class WeekOptionEnumDeserializer implements JsonDeserializer<WeekOptionEnum> {
    @Override
    public WeekOptionEnum deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return WeekOptionEnum.fromString(json.getAsString());
    }
}

