package sw.iot.droid.copy;

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
 * @author admin  on 2019/1/25.
 */
public class BeatBox {
    private static final String TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private AssetManager mAssetManager;
    private List<Sound> mSoundList;
    /**
     * 用于播放声音
     */
    private SoundPool mSoundPool;
    /**
     * 同时最多播放音频的数量
     */
    private static final int MAX_SOUNDS = 5;

    public BeatBox(Context context) {
        mAssetManager = context.getAssets();
        mSoundList = new ArrayList<>();
//        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        AudioAttributes attributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
        mSoundPool = new SoundPool.Builder().setMaxStreams(MAX_SOUNDS).setAudioAttributes(attributes).build();
        loadSounds();
    }

    private void loadSounds() {
        String[] sounds;
        try {
            sounds = mAssetManager.list(SOUNDS_FOLDER);
            if (sounds != null) {
                Log.d(TAG, "loadSounds: " + sounds.length);
                for (String fileName : sounds) {
                    //TODO  生成音频对象时 需要完整的路径!!
                    String assetPath = SOUNDS_FOLDER + "/" + fileName;
                    Sound sound = new Sound(assetPath);
                    load(sound);
                    mSoundList.add(sound);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "loadSounds: ", e);
        }
    }

    /**
     * TODO: 2019/1/25 AssetManager加载Asset文件？
     */
    private void load(Sound sound) throws IOException {
        AssetFileDescriptor descriptor = mAssetManager.openFd(sound.getAssetPath());
        //如果加载不是音频文件的，是不是马上就爆掉了？
        int soundId = mSoundPool.load(descriptor, 1);
        sound.setSoundId(soundId);
    }

    public List<Sound> getSoundList() {
        return mSoundList;
    }

    //指定播放声音
    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
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
     * 附着的Activity或者Fragment 销毁时 应当释放自身占用的资源：SoundPool对象
     */
    public void release() {
        mSoundPool.release();
    }
}
