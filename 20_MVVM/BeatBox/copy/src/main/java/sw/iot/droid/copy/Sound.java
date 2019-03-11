package sw.iot.droid.copy;

/**
 * 音频文件
 *
 * @author admin  on 2019/1/25.
 */
public class Sound {
    private String mAssetPath;
    private String mName;
    /**
     * SoundPool 预加载时来读取的关键字
     */
    private int mSoundId;

    public Sound(String assetPath) {
        this.mAssetPath = assetPath;
        //TODO 不能使用File.pathSeparator 路径分隔符 来替换"/"
        //从相对路径中拿到文件的名字，不包括后缀
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];
        mName = filename.replace(".wav", "");
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }


    public int getSoundId() {
        return mSoundId;
    }

    public void setSoundId(int soundId) {
        mSoundId = soundId;
    }
}
