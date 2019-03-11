package sw.iot.droid.copy2;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import sw.iot.droid.copy2.databinding.ActivityMainBinding;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity {
    /**
     * 盒子，拥有多个音频播放的载体
     */
    private BeatBox mBeatBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_main, null, false);
        setContentView(activityMainBinding.getRoot());
        mBeatBox = new BeatBox(this);

        activityMainBinding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        activityMainBinding.recyclerView.setAdapter(new SoundAdapter(this, mBeatBox));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }
}
