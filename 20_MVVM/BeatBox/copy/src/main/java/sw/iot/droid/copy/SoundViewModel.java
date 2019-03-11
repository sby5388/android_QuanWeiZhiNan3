package sw.iot.droid.copy;

import android.databinding.BaseObservable;

/**
 * 声音-视图-模型
 *
 * @author admin  on 2019/1/25.
 */
public class SoundViewModel extends BaseObservable {
    private Sound mSound;
    private BeatBox mBeatBox;

    public SoundViewModel(BeatBox beatBox) {
        this.mBeatBox = beatBox;
    }

    public Sound getSound() {
        return mSound;
    }

    /**
     * notifyChange(){@link BaseObservable}
     */
    public void setSound(Sound sound) {
        mSound = sound;
        // TODO 设置数据的时候，通知item 更新，如同BaseAdapter的NotifiedChanged() ，
        // TODO 避免了显示内容与页面不匹配的情况，接口来自{@link BaseObservable}
        notifyChange();
    }

    public BeatBox getBeatBox() {
        return mBeatBox;
    }


    public String getTitle() {
        return mSound.getName();
    }

    /**
     * 点击事件的方法，如同Activity中的onclick(view:View)
     */
    public void onButtonClicked() {
        mBeatBox.play(mSound);
    }
}
