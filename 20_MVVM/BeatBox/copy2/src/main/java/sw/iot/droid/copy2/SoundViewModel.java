package sw.iot.droid.copy2;

import android.databinding.BaseObservable;
import android.util.Log;

/**
 * @author admin  on 2019/3/11.
 */
public class SoundViewModel extends BaseObservable {
    private static final String TAG = "SoundViewModel";
    private Sound mSound;
    private BeatBox mBeatBox;


    public SoundViewModel(BeatBox beatBox) {
        mBeatBox = beatBox;
    }

    public void setSound(Sound sound) {
        mSound = sound;
        //回收视图更新item时 同步更新view显示
        //TODO
        notifyChange();
    }

    public String getTitle() {
        return mSound.getName();
    }

    public void onButtonClicked() {
        Log.d(TAG, "onButtonClicked: 点击 " + mSound.getName());
        //点击事件，播放声音
        mBeatBox.play(mSound);
    }
}
