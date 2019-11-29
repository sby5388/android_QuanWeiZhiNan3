package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Administrator  on 2019/11/29.
 */
public class TimePickerFragment extends DialogFragment {
    private static final String TAG = "TimePickerFragment";
    public static final String EXTRA_TIME = "com.bignerdranch.android.criminalintent.time";
    private static final String ARG_DATE = "date";

    private Calendar mCalendar;

    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(final Date date) {
        final TimePickerFragment timePickerFragment = new TimePickerFragment();
        final Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        timePickerFragment.setArguments(args);
        return timePickerFragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Bundle arguments = getArguments();
        if (arguments == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        final Date date = (Date) arguments.getSerializable(ARG_DATE);
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(date);
        final int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        final int minute = mCalendar.get(Calendar.MINUTE);
        final int second = mCalendar.get(Calendar.SECOND);

        final View view = LayoutInflater.from(getContext()).
                inflate(R.layout.dialog_time, null);
        mTimePicker = view.findViewById(R.id.dialog_time_picker);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // TODO: 2019/11/29
                Log.d(TAG, "onTimeChanged: hourOfDay = " + hourOfDay);
                Log.d(TAG, "onTimeChanged: minute =" + minute);

                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);
            }
        });
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult();
                    }
                })
                .create();
    }

    private void sendResult() {
        final Fragment targetFragment = getTargetFragment();
        if (targetFragment == null) {
            return;
        }
        final Intent intent = new Intent();
        final Date date = new Date();
        date.setTime(mCalendar.getTimeInMillis());
        intent.putExtra(EXTRA_TIME, date);

        targetFragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

    }
}
