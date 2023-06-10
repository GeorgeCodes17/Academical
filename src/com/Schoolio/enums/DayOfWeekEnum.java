package com.Schoolio.enums;

public enum DayOfWeekEnum {
    MON,
    TUE,
    WED,
    THU,
    FRI,
    SAT,
    SUN;

    public static DayOfWeekEnum fromString(String str) {
        switch (str) {
            case "Mon":
                return DayOfWeekEnum.MON;
            case "Tue":
                return DayOfWeekEnum.TUE;
            case "Wed":
                return DayOfWeekEnum.WED;
            case "Thu":
                return DayOfWeekEnum.THU;
            case "Fri":
                return DayOfWeekEnum.FRI;
            case "Sat":
                return DayOfWeekEnum.SAT;
            case "Sun":
                return DayOfWeekEnum.SUN;
            default:
                return DayOfWeekEnum.MON;
        }
    }
}
