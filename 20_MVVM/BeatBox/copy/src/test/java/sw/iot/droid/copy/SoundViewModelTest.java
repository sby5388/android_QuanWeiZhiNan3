package sw.iot.droid.copy;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author admin  on 2019/1/25.
 */
public class SoundViewModelTest {
    private BeatBox mBeatBox;
    private Sound mSound;
    /**
     * 被测试的对象
     */
    private SoundViewModel mSubject;

    @Before
    public void setUp() throws Exception {
        //TODO mock(Class)方法会 自动创建一个虚拟版本的BeatBox
        mBeatBox = mock(BeatBox.class);
        mSound = new Sound("assetPath");
        mSubject = new SoundViewModel(mBeatBox);
        mSubject.setSound(mSound);
    }

    @Test
    public void exposesSoundNameAsTitle() {
        assertThat(mSubject.getTitle(), is(mSound.getName()));
    }

    /**
     * 测试按钮点击事件
     */
    @Test
    public void callBeatBoxPlayOnClickButton() {
        mSubject.onButtonClicked();
        //通过mock检测判断BeatBox对象是否调用了Sound对象
        //调用verify(Object)方法，确认onButtonClicked()方法调用了 BeatBox.play(Sound)方法。
        verify(mBeatBox).play(mSound);
    }

}