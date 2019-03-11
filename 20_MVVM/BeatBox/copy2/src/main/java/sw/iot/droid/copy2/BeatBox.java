package sw.iot.droid.copy2;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin  on 2019/3/11.
 */
public class BeatBox {
    private static final String TAG = "BeatBox";
    private List<Sound> mSounds = new ArrayList<>();
    private AssetManager mAssetManager;
    private static final String SAMPLE_SOUNDS = "sample_sounds";
    private SoundPool mSoundPool;
    /**
     * 同时最多播放音频的数量
     */
    private static final int MAX_SOUNDS = 5;

    public BeatBox(Context context) {
        mAssetManager = context.getAssets();
        AudioAttributes attributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();

        mSoundPool = new SoundPool.Builder().setMaxStreams(MAX_SOUNDS).setAudioAttributes(attributes).build();
        loadSounds();
    }

    /**
     * SoundPool:特点是能够马上播放，但是必须要提前加载好
     */
    private void loadSounds() {
        try {
            String[] pathList = mAssetManager.list(SAMPLE_SOUNDS);
            if (pathList != null) {
                for (String path : pathList) {
                    //TODO  生成音频对象时 需要完整的路径!!
                    Sound sound = new Sound(SAMPLE_SOUNDS + "/" + path);
                    load(sound);
                    mSounds.add(sound);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "loadSounds: ", e);
        }
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor descriptor = mAssetManager.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(descriptor, 1);
        sound.setSoundId(soundId);
    }

    /**
     * 点击后播放
     */
    void play(Sound sound) {
        int soundId = sound.getSoundId();
        if (soundId == -1) {
            return;
        }
        //左音量
        float leftVolume = 1.0f;
        //右音量
        float rightVolume = 1.0f;
        //优先级,0为最低
        int priority = 1;
        //循环，0：不循环，-1 一直循环
        int loop = 0;
        //播放速率，1.0为正常速率
        float rate = 1.0f;

        mSoundPool.play(soundId, leftVolume, rightVolume, priority, loop, rate);
    }

    /**
     * 释放资源
     */
    void release(){
        mSoundPool.release();
    }
}
