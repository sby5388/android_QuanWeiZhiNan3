package com.android.copy.view.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.copy.view.R;

/**
 * @author Administrator  on 2019/6/17.
 */
class ToolsViewHolder extends RecyclerView.ViewHolder {
     TextView mTextView;

    ToolsViewHolder(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.textView);
    }
}
