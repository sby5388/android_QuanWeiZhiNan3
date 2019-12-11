package com.by5388.demo.beatbox2;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.by5388.demo.beatbox2.databinding.ItemBeatboxBinding;


/**
 * @author Administrator  on 2019/12/4.
 */
class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundHolder> {
    private BeatBox mBeatBox;

    SoundAdapter(BeatBox beatBox) {
        mBeatBox = beatBox;
    }

    @NonNull
    @Override
    public SoundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemBeatboxBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_beatbox, parent, false);
        return new SoundHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundHolder holder, int position) {
        final Sound sound = mBeatBox.getSounds().get(position);
        holder.bind(sound);
    }

    @Override
    public int getItemCount() {
        return mBeatBox.getSounds().size();
    }

    class SoundHolder extends RecyclerView.ViewHolder {
        private final ItemBeatboxBinding mBinding;

        SoundHolder(@NonNull ItemBeatboxBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setViewModel(new SoundViewModel(SoundAdapter.this.mBeatBox));
        }

        private void bind(Sound sound) {
            mBinding.getViewModel().setSound(sound);
            // TODO: 2019/12/5 更新绑定的视图，必须运行在uiThread上
            //  一般不需要调用，但为了迫使布局立即刷新
            mBinding.executePendingBindings();
        }


    }
}
