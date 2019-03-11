package sw.iot.droid.copy2;

/**
 * @author admin  on 2019/3/11.
 */
public class Sound {
    /**
     * 真实路径
     */
    private String mAssetPath;
    /**
     * 显示名称：只有文件名称
     */
    private String mName;
    /**
     * SoundPool 预加载时来读取的关键字
     */
    private int mSoundId;

    public Sound(String assetPath) {
        mAssetPath = assetPath;
        String[] split = assetPath.split("/");
        String name = split[split.length - 1];
        mName = name.replace(".wav", "");
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public void setAssetPath(String assetPath) {
        mAssetPath = assetPath;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getSoundId() {
        return mSoundId;
    }

    public void setSoundId(int soundId) {
        mSoundId = soundId;
    }
}
