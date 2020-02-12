package com.android.copy.view.temp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.copy.view.R;

import java.util.ArrayList;
import java.util.List;

public class TempActivity extends AppCompatActivity {

    private Toast mToast;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        mToast = Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT);
        initData();
        initView();
    }

    private void initView() {
        mView = getLayoutInflater().inflate(R.layout.temp_drawable, new LinearLayout(this), false);
    }

    private void initData() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(new TempAdapter());
    }

    private class TempAdapter extends RecyclerView.Adapter<TempViewHolder> {
        private List<Integer> items;

        TempAdapter() {
            items = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                items.add(i);
            }
        }

        @NonNull
        @Override
        public TempViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final Context context = parent.getContext();
            final LayoutInflater inflater = LayoutInflater.from(context);
            final View rootView = inflater.inflate(R.layout.item_temp, parent, false);
            return new TempViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(@NonNull final TempViewHolder holder, int position) {
            final String showText = "第" + position + "项";
            holder.mTextView.setText(showText);
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Toast toast = Toast.makeText(getApplicationContext(), showText, Toast.LENGTH_SHORT);
                    // TODO: 2019/6/17 自定义Toast的显示的内容，不仅仅是文本
                    toast.setView(mView);
                    toast.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private class TempViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextView;

        TempViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textView_temp);
        }
    }
}
