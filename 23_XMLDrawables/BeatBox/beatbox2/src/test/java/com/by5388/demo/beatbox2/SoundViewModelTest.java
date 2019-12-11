package com.by5388.demo.beatbox2;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * 单元测试：mock 生成一个观察对象，
 *
 * @author Administrator  on 2019/12/5.
 */
public class SoundViewModelTest {

    private BeatBox mBeatBox;
    private Sound mSound;
    private SoundViewModel mSubject;

    @Before
    public void setUp() throws Exception {
        mBeatBox = Mockito.mock(BeatBox.class);
        mSound = new Sound("assetPath");
        mSubject = new SoundViewModel(mBeatBox);
        mSubject.setSound(mSound);
    }

    @Test
    public void exposesSoundNameAsTitle() {
        Assert.assertThat(mSubject.getTitle(), Is.is(mSound.getName()));

    }


    @Test
    public void callsBeatBoxPlayOnButtonClicked() {
        mSubject.play();
        // TODO: 2019/12/5 verify "我要验证mBeatBox对象的某个方法是否调用了"
        Mockito.verify(mBeatBox).play(mSound);
        // TODO: 2019/12/5 下面这种方法测试失败，
        //  因为mSubject 不是Mockito#mock() 生成的
        //  Mockito.verify(mSubject).play();
    }
}