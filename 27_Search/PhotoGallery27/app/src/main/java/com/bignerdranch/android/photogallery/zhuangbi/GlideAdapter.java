package com.bignerdranch.android.photogallery.zhuangbi;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bignerdranch.android.photogallery.R;
import com.bumptech.glide.Glide;

/**
 * @author admin  on 2019/3/13.
 */
public class GlideAdapter extends ZhuangbiAdapter {
    public GlideAdapter(Context context, ZbDownloadHandlerThread<ZhuangbiHolder> handlerThread) {
        super(context, handlerThread);
    }

    @Override
    public void onBindViewHolder(@NonNull ZhuangbiHolder holder, int position) {
        ZhuangbiItem zhuangbiItem = mZhuangbiItemList.get(position);
        Glide.with(mContext)
                .load(zhuangbiItem.getUrl())
                .placeholder(R.drawable.bill_up_close)
                .into(holder.mImageView);
    }
}
