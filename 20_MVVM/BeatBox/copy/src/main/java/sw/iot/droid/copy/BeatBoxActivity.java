package sw.iot.droid.copy;

import android.support.v4.app.Fragment;

/**
 * @author Administrator
 */
public class BeatBoxActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return BeatBoxFragment.newInstance();
    }
}
