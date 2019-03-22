package com.bignerdranch.android.photogallery.zhuangbi;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bignerdranch.android.photogallery.R;

/**
 * @author admin  on 2019/3/13.
 */
public class ZhuangbiHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView mImageView;
    private ZhuangbiItem mItem;
    private ZhuangbiOnclickListener mListener;

    ZhuangbiHolder(View itemView, ZhuangbiOnclickListener listener) {
        super(itemView);
        itemView.setOnClickListener(this);
        mImageView = (ImageView) itemView.findViewById(R.id.item_image_view);
        this.mListener = listener;
    }

    void bindItem(ZhuangbiItem item) {
        this.mItem = item;
    }


    void bindZhuangbiItem(Drawable drawable) {
        mImageView.setImageDrawable(drawable);
    }

    @Override
    public void onClick(View v) {
        mListener.pushUri(Uri.parse(mItem.getUrl()));
    }
}
