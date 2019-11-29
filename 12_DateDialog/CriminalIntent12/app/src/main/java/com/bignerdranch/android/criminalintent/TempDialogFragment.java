package com.bignerdranch.android.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.util.Date;

/**
 * @author admin  on 2019/2/19.
 */
public class TempDialogFragment extends DialogFragment {
    private static final String BUNDLE_NAME = "temp_bundle";
    private static final String DATE_NAME = "date_name";

    public static DialogFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(DATE_NAME, date);
        TempDialogFragment fragment = new TempDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            Date date = (Date) args.getSerializable(DATE_NAME);
        }
        return super.onCreateDialog(savedInstanceState);
    }

    public TempDialogFragment() {
    }
}
