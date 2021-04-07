package com.justnik.justtasks.taskdb;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class DateConverter {

    @TypeConverter
    public static Calendar fromTimestamp(Long l) {
        if (l == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);
        return c;
    }

    @TypeConverter
    public static Long toTimestamp(Calendar c) {
        return c == null ? null : c.getTimeInMillis();
    }

}
