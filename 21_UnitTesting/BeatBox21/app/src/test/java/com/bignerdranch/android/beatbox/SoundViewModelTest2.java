package com.bignerdranch.android.beatbox;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author admin  on 2019/3/11.
 */
public class SoundViewModelTest2 {
    private BeatBox mBeatBox;
    private Sound mSound;
    private SoundViewModel mSubject;

    @Before
    public void setUp() throws Exception {
        mBeatBox = mock(BeatBox.class);
        mSound = new Sound("assetPath");
        mSubject = new SoundViewModel(mBeatBox);
        mSubject.setSound(mSound);
    }

    @Test
    public void testName() {
        assertThat(mSubject.getTitle(), is(mSound.getName()));
    }

    @Test
    public void getTitle() {
    }

    @Test
    public void getSound() {
    }

    @Test
    public void setSound() {
    }

    @Test
    public void onButtonClicked() {
    }

    @Test
    public void testOnclick() {
        mSubject.onButtonClicked();
        verify(mBeatBox).play(mSound);

    }
}