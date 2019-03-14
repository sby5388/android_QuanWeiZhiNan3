package com.bignerdranch.android.photogallery.zhuangbi;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bignerdranch.android.photogallery.R;
import com.squareup.picasso.Picasso;

/**
 * @author admin  on 2019/3/13.
 */
@SuppressWarnings("unused")
public class PicassoAdapter extends ZhuangbiAdapter {
    public PicassoAdapter(Context context, ZbDownloadHandlerThread<ZhuangbiHolder> handlerThread) {
        super(context, handlerThread);
    }

    @Override
    public void onBindViewHolder(@NonNull ZhuangbiHolder holder, int position) {
        ZhuangbiItem zhuangbiItem = mZhuangbiItemList.get(position);
        Picasso.with(mContext)
                .load(zhuangbiItem.getUrl())
                .placeholder(R.drawable.bill_up_close)
                .into(holder.mImageView);
    }
}
