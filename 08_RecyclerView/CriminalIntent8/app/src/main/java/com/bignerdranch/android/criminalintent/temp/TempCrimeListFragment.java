package com.bignerdranch.android.criminalintent.temp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.criminalintent.R;

/**
 * @author Administrator  on 2019/10/22.
 */
public class TempCrimeListFragment extends Fragment {


    private RecyclerView mRecyclerView;

    private TempCrimeAdapter mAdapter;

    public static Fragment newInstance() {
        return new TempCrimeListFragment();
    }

    public TempCrimeListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 2019/10/22 生成模拟的数据对象
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO: 2019/10/22 初始化视图数据
        final View view = inflater.inflate(R.layout.temp_fragment_crime_list, container, false);
        mRecyclerView = view.findViewById(R.id.temp_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;


    }
}
