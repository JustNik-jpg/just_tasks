package com.justnik.justtasks.view.datepicker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.justnik.justtasks.view.datepicker.OnDateSelectListener;

import java.util.Calendar;

public class DateTimePicker {

    @NonNull
    private final Calendar calendar = Calendar.getInstance();
    @Nullable
    private DatePickerDialog datePickerDialog;
    @Nullable
    private TimePickerDialog timePickerDialog;

    private OnDateSelectListener dateSelectListener;

    public DateTimePicker(OnDateSelectListener dateSelectListener) {
        this.dateSelectListener = dateSelectListener;
    }

    public void showDialog(@NonNull Context context, long time) {
        calendar.setTimeInMillis(time);
        closeDialogs();
        showDatePicker(context);
    }

    public long getTime() {
        return calendar.getTimeInMillis();
    }

    private void closeDialogs() {
        if (datePickerDialog != null) {
            datePickerDialog.dismiss();
            datePickerDialog = null;
        }
        if (timePickerDialog != null) {
            timePickerDialog.dismiss();
            timePickerDialog = null;
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        timePicker(view.getContext());
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND,0);
            if (dateSelectListener != null) {
                dateSelectListener.onDate(calendar);
            }
        }
    };

    private void showDatePicker(@NonNull Context context) {
        datePickerDialog = new DatePickerDialog(context,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void timePicker(@NonNull Context context) {
        timePickerDialog = new TimePickerDialog(context,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        timePickerDialog.show();
    }

    public void release() {
        closeDialogs();
        dateSelectListener = null;
    }

}