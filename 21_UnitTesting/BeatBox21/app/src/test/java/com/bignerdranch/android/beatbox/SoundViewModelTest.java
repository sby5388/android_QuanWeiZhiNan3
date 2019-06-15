package com.bignerdranch.android.beatbox;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SoundViewModelTest {
    private BeatBox mBeatBox;
    private Sound mSound;
    //mSubject : 被测试的主题，
    private SoundViewModel mSubject;

    @Before
    public void setUp() throws Exception {
        //创建虚拟的BeatBox对象
        mBeatBox = mock(BeatBox.class);
        mSound = new Sound("assetPath");
        mSubject = new SoundViewModel(mBeatBox);
        mSubject.setSound(mSound);
    }

    @Test
    public void exposesSoundNameAsTitle() {
        assertThat(mSubject.getTitle(), is(mSound.getName()));
    }

    @Test
    public void callsBeatBoxPlayOnButtonClicked() {
        mSubject.onButtonClicked();
        // TODO: 2019/6/14 检测 mBeatBox 是否调用了play(Sound)的方法
        //Mockito虚拟对象都能自我跟踪管理哪些方法调用了，以及都传入了哪些参数。
        verify(mBeatBox).play(mSound);
    }
}