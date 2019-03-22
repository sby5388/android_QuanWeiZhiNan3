package com.bignerdranch.android.photogallery.zhuangbi;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bignerdranch.android.photogallery.R;

/**
 * @author admin  on 2019/3/13.
 */
public class ZhuangbiHolder extends RecyclerView.ViewHolder {
    ImageView mImageView;

    ZhuangbiHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.item_image_view);
    }

    void bindZhuangbiItem(Drawable drawable) {
        mImageView.setImageDrawable(drawable);
    }
}
