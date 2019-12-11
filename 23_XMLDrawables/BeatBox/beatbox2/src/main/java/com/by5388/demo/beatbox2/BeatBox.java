package com.by5388.demo.beatbox2;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator  on 2019/12/4.
 */
public class BeatBox {
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final String TAG = "BeatBox2";

    private AssetManager mAssetManager;
    private final SoundPool mSoundPool;
    private static final int MAX_SOUNDS = 5;
    private List<Sound> mSounds = new ArrayList<>();
    private float mRate = 1.0f;


    public static BeatBox getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final BeatBox INSTANCE = new BeatBox(BeatBoxApplication.getInstance());
    }

    private BeatBox(final Context context) {
        mAssetManager = context.getAssets();
        mSoundPool = createSoundPool();
        try {
            loadSounds();
        } catch (IOException e) {
            // TODO: 2019/12/4
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private SoundPool createSoundPool() {
        if (true) {
            // TODO: 2019/12/5  出现了插耳机也是外放，所以使用旧的API
            return new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        }

        // TODO: 2019/12/5 也许需要为旧版本提供相应的api适配
        return new SoundPool.Builder()
                .setMaxStreams(MAX_SOUNDS)
                .setAudioAttributes(createAudioAttributes())
                .build();
    }

    private AudioAttributes createAudioAttributes() {
        // TODO: 2019/12/5 不知道这些参数配置对不对
        return new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                // TODO: 2019/12/5 出现了插耳机也是外放，不知道是不是这里的原因
//                .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build();
    }


    private void loadSounds() throws IOException {
        final String[] fileArray = mAssetManager.list(SOUNDS_FOLDER);
        if (fileArray == null || fileArray.length == 0) {
            return;
        }

        for (String fileName : fileArray) {
            try {
                final String assetPath = SOUNDS_FOLDER + "/" + fileName;
                final Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException e) {
                Log.d(TAG, "loadSounds: Could not load sound" + fileName, e);
            }

        }
    }


    private void load(Sound sound) throws IOException {
        final AssetFileDescriptor fileDescriptor = mAssetManager.openFd(sound.getAssetPath());
        final int soundId = mSoundPool.load(fileDescriptor, 1);
        sound.setSoundId(soundId);
        // TODO: 2019/12/5
//        fileDescriptor.close();
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    /**
     * 释放
     */
    public void release() {
        mSoundPool.release();
    }


    public void play(Sound sound) {
        if (sound == null) {
            return;
        }
        final Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        //左声道(0.0,1.0)
        final float leftVolume = 1.0f;
        //右声道(0.0,1.0)
        final float rightVolume = 1.0f;
        //优先级,0:最低
        final int priority = 1;
        //循环，0：不循环，1：一直循环
        final int loop = 0;
        //播放速率：1.0正常，(0.5,2.0)
        final float rate = mRate;

        mSoundPool.play(soundId, leftVolume, rightVolume, priority, loop, rate);
    }

    public void setRate(int progress) {
        final int min = 50;
        final int max = 200;
        if (progress < min || progress > max) {
            return;
        }
        mRate = progress / 100.0f;

    }
}
