package com.Schoolio.objects;

import com.google.gson.annotations.SerializedName;

public class YearObject {
    @SerializedName("id") private final int id;
    @SerializedName("name") private final String name;

    public YearObject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
