package sw.iot.droid.copy2;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import sw.iot.droid.copy2.databinding.ItemSoundBinding;

/**
 * @author admin  on 2019/3/11.
 */
public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.ViewHolder> {

    private Context mContext;
    private BeatBox mBeatBox;
    private List<Sound> mSounds;

    public SoundAdapter(Context context,BeatBox beatBox) {
        mContext = context;
        mBeatBox = beatBox;
        mSounds = mBeatBox.getSounds();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSoundBinding itemSoundBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_sound, parent, false);
        return new ViewHolder(itemSoundBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sound sound = mSounds.get(position);
        holder.bind(sound);
    }

    @Override
    public int getItemCount() {
        return mSounds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSoundBinding mItemSoundBinding;

        public ViewHolder(ItemSoundBinding itemSoundBinding) {
            super(itemSoundBinding.getRoot());
            mItemSoundBinding = itemSoundBinding;
            mItemSoundBinding.setViewModel(new SoundViewModel(mBeatBox));
        }

        public void bind(Sound sound) {
            mItemSoundBinding.getViewModel().setSound(sound);
            // TODO: 2019/3/11 ??
            mItemSoundBinding.executePendingBindings();
        }
    }
}
