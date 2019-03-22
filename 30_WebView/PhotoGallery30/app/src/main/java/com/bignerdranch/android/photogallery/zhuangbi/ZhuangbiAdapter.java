package com.bignerdranch.android.photogallery.zhuangbi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.photogallery.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin  on 2019/2/13.
 */
public class ZhuangbiAdapter extends RecyclerView.Adapter<ZhuangbiHolder> {
    Context mContext;
    List<ZhuangbiItem> mZhuangbiItemList;

    ZhuangbiOnclickListener mListener;


    private ZbDownloadHandlerThread<ZhuangbiHolder> mHandlerThread;

    ZhuangbiAdapter(Context context, ZbDownloadHandlerThread<ZhuangbiHolder> handlerThread, ZhuangbiOnclickListener listener) {
        mContext = context;
        mZhuangbiItemList = new ArrayList<>();
        this.mHandlerThread = handlerThread;
        this.mListener = listener;
    }

    public void setZhuangbiItemList(List<ZhuangbiItem> zhuangbiItemList) {
        mZhuangbiItemList = zhuangbiItemList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ZhuangbiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        return new ZhuangbiHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_gallery, viewGroup, false), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ZhuangbiHolder holder, int position) {
        ZhuangbiItem zhuangbiItem = mZhuangbiItemList.get(position);
        holder.bindItem(zhuangbiItem);
        Drawable placeholder = mContext.getResources().getDrawable(R.drawable.bill_up_close);
        holder.bindZhuangbiItem(placeholder);
        mHandlerThread.queryThumbnail(holder, zhuangbiItem.getUrl());
    }

    @Override
    public int getItemCount() {
        return mZhuangbiItemList.size();
    }
}
