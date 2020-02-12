package com.android.copy.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.copy.view.fragment.ToolsFragment;
import com.android.copy.view.widget.MyLayout;

public class MainActivity extends AppCompatActivity implements ToolsFragment.ToolsUICallBack {
    private MyLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.my_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.reset == item.getItemId()) {
            mLayout.reset();
            return true;
        }
        if (R.id.color_blue == item.getItemId()) {
            changeColor(R.color.blue);
            return true;
        }
        if (R.id.color_red == item.getItemId()) {
            changeColor(R.color.red);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeColor(int colorValue) {
        if (mLayout != null) {
            mLayout.changeColor(colorValue);
        }
    }

    @Override
    public void addBrush() {
        if (mLayout != null) {
            mLayout.addBrush(R.drawable.brush_blue);
        }
    }
}

