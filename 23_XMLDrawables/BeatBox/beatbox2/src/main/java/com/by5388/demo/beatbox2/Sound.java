package com.by5388.demo.beatbox2;

/**
 * @author Administrator  on 2019/12/4.
 */
public class Sound {
    private final String mAssetPath;
    private final String mName;
    private Integer mSoundId;

    public Sound(String assetPath) {
        mAssetPath = assetPath;
        final String[] split = assetPath.split("/");
        final String fileName = split[split.length - 1];
        mName = fileName.replace(".wav", "");
    }

    public String getAssetPath() {
        return mAssetPath;
    }


    public String getName() {
        return mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }
}
