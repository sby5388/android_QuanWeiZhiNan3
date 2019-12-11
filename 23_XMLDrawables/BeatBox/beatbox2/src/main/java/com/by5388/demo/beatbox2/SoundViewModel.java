package com.by5388.demo.beatbox2;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.Toast;

/**
 * @author Administrator  on 2019/12/4.
 */
public class SoundViewModel extends BaseObservable {
    private Sound mSound;
    private Toast mToast;
    private final BeatBox mBeatBox;

    public SoundViewModel(BeatBox beatBox) {
        mBeatBox = beatBox;
    }

    public void setSound(Sound sound) {
        mSound = sound;
        notifyChange();
    }

    @Bindable
    public String getTitle() {
        return mSound.getName();
    }

    public void play() {
        // TODO: 2019/12/4
        /*
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(BeatBoxApplication.getInstance(), mSound.getName(), Toast.LENGTH_SHORT);
        mToast.show();
         */

        mBeatBox.play(mSound);
    }
}
