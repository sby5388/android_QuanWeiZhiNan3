package com.bignerdranch.android.photogallery.zhuangbi;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * @author admin  on 2019/2/13.
 */
public class ZhuangbiHolder extends RecyclerView.ViewHolder {
    private TextView mTitleTextView;

    public ZhuangbiHolder(View itemView) {
        super(itemView);

        mTitleTextView = (TextView) itemView;
    }

    public void bindGalleryItem(ZhuangbiItem item) {
        mTitleTextView.setText(item.getSHowText());
    }
}
