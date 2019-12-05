package sw.iot.droid.copy;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sw.iot.droid.copy.databinding.FragmentCopyBinding;
import sw.iot.droid.copy.databinding.ItemBeatBoxBinding;

/**
 * @author admin  on 2019/1/25.
 */
public class BeatBoxFragment extends Fragment {
    private BeatBox mBeatBox;

    public static Fragment newInstance() {
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 2019/1/26 保存一个当前的Fragment的状态，包括所拥有的数据，
        // TODO 屏幕发生旋转，Activity 被销毁时 这样就能在某些情况下恢复到原来的状态。
        //  fragment的retainInstance属性值默认为false，这表明其不会被保留。
        //  因此，设备旋转时 fragment会随托管activity一起被销毁并重建。
        //  调用setRetainInstance(true)方法可保留fragment。
        //  已保留的fragment不会随activity一起被销毁。
        //  相反，它会一直保留，并在需要时原封 不动地转给新的activity。
        //  对于已保留的fragment实例，其全部实例变量（如mBeatBox）的值也会保持不变，因此可放心继续使用。
        //  运行BeatBox应用。播放69_ohm-loko声音文件，然后旋转设备，确认音频播放不受影响。
        //TODO 使用Fragment 来避免 因为屏幕翻转而带来的数据丢失的问题:就是保存Fragment的状态
        setRetainInstance(true);
        mBeatBox = new BeatBox(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCopyBinding fragmentCopyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_copy, container, false);

        fragmentCopyBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        // TODO: 2019/1/25 初始化数据
        fragmentCopyBinding.recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSoundList()));
        return fragmentCopyBinding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁时、旋转时 及时释放相应的资源
        mBeatBox.release();
    }

    /**
     * TODO 使用dataBinding数据绑定之后 确实省去了很多不必要的findViewById
     */
    private class SoundViewHolder extends RecyclerView.ViewHolder {
        private ItemBeatBoxBinding mBinding;

        SoundViewHolder(ItemBeatBoxBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            this.mBinding.setViewModel(new SoundViewModel(mBeatBox));
        }

        public void bind(Sound sound) {
            mBinding.getViewModel().setSound(sound);
            // TODO: 2019/1/25 不是太懂这一句的含义？
            mBinding.executePendingBindings();
        }
    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundViewHolder> {
        private List<Sound> mSoundList;

        SoundAdapter(List<Sound> soundList) {
            this.mSoundList = soundList;
        }

        @Override
        public SoundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ItemBeatBoxBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_beat_box, parent, false);

            return new SoundViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(SoundViewHolder holder, int position) {
            //holder.mBinding.button.setText(String.valueOf(position));
            Sound sound = mSoundList.get(position);
            holder.bind(sound);
        }

        @Override
        public int getItemCount() {
            return this.mSoundList.size();
        }
    }
}
