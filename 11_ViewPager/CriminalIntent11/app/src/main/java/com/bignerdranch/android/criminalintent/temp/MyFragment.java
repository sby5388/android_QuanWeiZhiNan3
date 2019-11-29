package com.bignerdranch.android.criminalintent.temp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.criminalintent.R;

import java.util.Calendar;
import java.util.Locale;

/**
 * @author Administrator  on 2019/11/29.
 */
public class MyFragment extends Fragment {
    private static final String TAG = "MyFragment";

    private static final String KEY_INDEX_VALUE = "key_index_value";
    private int mIndex = 0;
    private TextView mTextView;

    public static Fragment newInstance(int value) {
        final Fragment fragment = new MyFragment();
        final Bundle args = new Bundle();
        args.putInt(KEY_INDEX_VALUE, value);
        fragment.setArguments(args);
        return fragment;
    }

    public interface MyCallback {
        void onUpdate(int index);
    }

    private MyCallback mCallBack;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyCallback) {
            mCallBack = (MyCallback) context;
        } else {
            throw new RuntimeException("Error");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        mIndex = arguments.getInt(KEY_INDEX_VALUE, 0);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_temp, container, false);
        mTextView = view.findViewById(R.id.textView_show_name);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTextView.setText(String.format(Locale.getDefault(), "序号:%d", mIndex));
        Log.d(TAG, "onResume: " + Calendar.getInstance().getTime().getTime());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint: isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser && mCallBack != null) {
            Log.d(TAG, "setUserVisibleHint: mIndex = " + mIndex);
            mCallBack.onUpdate(mIndex);
        }
    }

}
