package com.bignerdranch.android.beatbox;

import android.support.v7.widget.RecyclerView;

import com.bignerdranch.android.beatbox.databinding.ListItemSoundBinding;

/**
 * @author Administrator  on 2019/6/14.
 */
public class SoundHolder extends RecyclerView.ViewHolder {
    private ListItemSoundBinding mBinding;

    SoundHolder(ListItemSoundBinding binding, BeatBox beatBox) {
        super(binding.getRoot());
        mBinding = binding;
        mBinding.setViewModel(new SoundViewModel(beatBox));
    }

    void bind(Sound sound) {
        mBinding.getViewModel().setSound(sound);
        mBinding.executePendingBindings();
    }
}