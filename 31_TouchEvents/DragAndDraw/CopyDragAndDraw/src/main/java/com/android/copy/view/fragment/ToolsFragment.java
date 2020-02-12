package com.android.copy.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.copy.view.temp.TempActivity;

/**
 * @author Administrator  on 2019/6/17.
 */
public abstract class ToolsFragment extends Fragment implements ToolsAdapter.OnClickListener {
    public static final String TAG = "ToolsFragment";
    protected RecyclerView mRecyclerView;
    protected ToolsUICallBack mCallBack;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToolsUICallBack) {
            mCallBack = (ToolsUICallBack) context;
        } else {
            throw new RuntimeException("context must implements ToolsFragment.ToolsUICallBack");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayoutId(), container, false);
    }

    @LayoutRes
    protected abstract int getFragmentLayoutId();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = getRecyclerView();
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(getAdapter());
    }

    protected abstract RecyclerView getRecyclerView();

    protected abstract RecyclerView.LayoutManager getLayoutManager();


    protected abstract RecyclerView.Adapter getAdapter();


    public interface ToolsUICallBack {
        /**
         * 添加一把刷子
         */
        void addBrush();
    }

    @Override
    public void onClickAction(int position) {
        Log.d(TAG, "onClickAction: position = " + position);
        if (position == 0) {
            mCallBack.addBrush();
        } else if (position == 4) {
            test();
        }
    }

    protected void test() {
        final Context context = getContext();
        if (context == null) {
            Log.e(TAG, "test: ", new NullPointerException("context == null"));
            return;
        }
        ContextCompat.startActivity(context, new Intent(context, TempActivity.class), null);
    }

    @NonNull
    @Override
    public View getView() {
        final View view = super.getView();
        if (view == null) {
            throw new NullPointerException("RootView is null");
        }
        return view;

    }
}
