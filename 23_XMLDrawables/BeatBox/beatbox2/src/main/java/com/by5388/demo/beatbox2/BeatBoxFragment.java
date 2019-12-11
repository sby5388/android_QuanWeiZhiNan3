package com.by5388.demo.beatbox2;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.by5388.demo.beatbox2.databinding.FragmentBeatboxBindingImpl;


/**
 * @author Administrator  on 2019/12/4.
 */
public class BeatBoxFragment extends Fragment {
    private static final String TAG = BeatBoxFragment.class.getSimpleName();

    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private BeatBox mBeatBox;
    private boolean mFromUser = true;

    public static Fragment newInstance() {
        return new BeatBoxFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 2019/12/5 在Activity发生屏幕旋转时保留Fragment对象，
        setRetainInstance(true);
        mBeatBox = BeatBox.getInstance();
        mAdapter = new SoundAdapter(mBeatBox);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBeatboxBindingImpl binding = DataBindingUtil.inflate(inflater, R.layout.fragment_beatbox, container, false);
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setAdapter(mAdapter);
        final AppCompatSeekBar seekBar = binding.seekBar;
        seekBar.setOnSeekBarChangeListener(createSeekBarOnSeekBarChangeListener());
        
        binding.setFragment(this);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    private SeekBar.OnSeekBarChangeListener createSeekBarOnSeekBarChangeListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mFromUser = fromUser;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!mFromUser) {
                    return;
                }
                mBeatBox.setRate(seekBar.getProgress());
            }
        };
    }

}
