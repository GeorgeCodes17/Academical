package com.Schoolio.objects;

import com.Schoolio.enums.DayOfWeekEnum;
import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.sql.Timestamp;

public class LessonScheduleObject {
    @SerializedName("id") private final int id;
    @SerializedName("lesson") private final LessonObject lesson;
    @SerializedName("year") private final YearObject year;
    @SerializedName("day_of_week") private final DayOfWeekEnum dayOfWeek;
    @SerializedName("start") private final Time start;
    @SerializedName("end") private final Time end;
    @SerializedName("assigned_by") private final String assignedBy;
    @SerializedName("created_at") private final Timestamp createdAt;
    @SerializedName("updated_at") private final Timestamp updatedAt;

    public LessonScheduleObject(
            int id,
            LessonObject lesson,
            YearObject year,
            DayOfWeekEnum dayOfWeek,
            Time start,
            Time end,
            String assignedBy,
            Timestamp createdAt,
            Timestamp updatedAt
    ) {
        this.id = id;
        this.lesson = lesson;
        this.year = year;
        this.dayOfWeek = dayOfWeek;
        this.start = start;
        this.end = end;
        this.assignedBy = assignedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public LessonObject getLesson() {
        return lesson;
    }

    public YearObject getYear() {
        return year;
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
