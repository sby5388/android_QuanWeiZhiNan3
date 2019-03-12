package com.by5388.photogallery;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.by5388.photogallery.use.loader.ShowItem;

/**
 * @author admin  on 2019/2/13.
 */
public class ShowHolder extends RecyclerView.ViewHolder {
    private TextView mTitleTextView;

    public ShowHolder(View itemView) {
        super(itemView);

        mTitleTextView = (TextView) itemView;
    }

    public void bindGalleryItem(ShowItem item) {
        mTitleTextView.setText(item.getSHowText());
    }
}
