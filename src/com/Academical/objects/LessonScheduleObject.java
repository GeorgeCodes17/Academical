package com.Academical.objects;

import com.Academical.enums.DayOfWeekEnum;
import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.sql.Timestamp;

public class LessonScheduleObject {
    @SerializedName("id") private Integer id = null;
    @SerializedName("lesson") private final LessonObject lesson;
    @SerializedName("day_of_week") private final DayOfWeekEnum dayOfWeek;
    @SerializedName("start") private final Time start;
    @SerializedName("end") private final Time end;
    @SerializedName("assigned_by") private String assignedBy = null;
    @SerializedName("created_at") private Timestamp createdAt = null;
    @SerializedName("updated_at") private Timestamp updatedAt = null;

    public LessonScheduleObject(
            int id,
            LessonObject lesson,
            DayOfWeekEnum dayOfWeek,
            Time start,
            Time end,
            String assignedBy,
            Timestamp createdAt,
            Timestamp updatedAt
    ) {
        this.id = id;
        this.lesson = lesson;
        this.dayOfWeek = dayOfWeek;
        this.start = start;
        this.end = end;
        this.assignedBy = assignedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LessonScheduleObject(
            LessonObject lesson,
            DayOfWeekEnum dayOfWeek,
            Time start,
            Time end
    ) {
        this.lesson = lesson;
        this.dayOfWeek = dayOfWeek;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public LessonObject getLesson() {
        return lesson;
    }

    public DayOfWeekEnum getDayOfWeek() {
        return dayOfWeek;
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}
