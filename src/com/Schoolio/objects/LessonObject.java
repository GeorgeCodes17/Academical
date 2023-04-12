package com.Schoolio.objects;

public class LessonObject {
    private final int id;
    private final String name;
    private int fkYear;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public LessonObject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFkYear() {
        return fkYear;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }
}
