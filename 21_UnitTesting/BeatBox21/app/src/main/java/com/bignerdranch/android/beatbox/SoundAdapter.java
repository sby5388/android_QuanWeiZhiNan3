package com.bignerdranch.android.beatbox;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bignerdranch.android.beatbox.databinding.ListItemSoundBinding;

import java.util.List;

/**
 * @author Administrator  on 2019/6/14.
 */
public class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {
    private List<Sound> mSounds;
    private BeatBox mBeatBox;

    public SoundAdapter(BeatBox beatBox) {
        mBeatBox = beatBox;
        mSounds = mBeatBox.getSounds();
    }

    @Override
    public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemSoundBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.list_item_sound, parent, false);
        return new SoundHolder(binding, mBeatBox);
    }

    @Override
    public void onBindViewHolder(SoundHolder holder, int position) {
        Sound sound = mSounds.get(position);
        holder.bind(sound);
    }

    @Override
    public int getItemCount() {
        return mSounds.size();
    }
}