package com.Schoolio.objects;

public class YearObject {
    private final int id;
    private final String name;

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
