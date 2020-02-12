package com.android.copy.view.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.copy.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator  on 2019/6/17.
 */
public class ToolsAdapter extends RecyclerView.Adapter<ToolsViewHolder> implements View.OnClickListener {
    private final List<String> text = new ArrayList<>();
    private OnClickListener mOnClickListener;

    ToolsAdapter() {
        initData();
    }

    private void initData() {
        text.clear();
        text.add("添加一个笔");
        text.add("文本2");
        text.add("文本3");
        text.add("文本4");
        text.add("TempActivity");

    }

    ToolsAdapter(OnClickListener onClickListener) {
        initData();
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ToolsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_tools, parent, false);
        return new ToolsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToolsViewHolder holder, int position) {
        final String s = text.get(position);
        holder.mTextView.setText(s);
        holder.mTextView.setTag(position);
        holder.mTextView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return text.size();
    }

    @Override
    public void onClick(View v) {
        final int position = (Integer) v.getTag();
        if (mOnClickListener != null) {
            mOnClickListener.onClickAction(position);
        }
    }

    public interface OnClickListener {
        void onClickAction(int position);
    }
}
