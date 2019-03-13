package com.by5388.photogallery.zhuang.bi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin  on 2019/2/13.
 */
public class ZhuangbiAdapter extends RecyclerView.Adapter<ZhuangbiHolder> {
    private Context mContext;
    private List<ZhuangbiItem> mZhuangbiItemList;

    ZhuangbiAdapter(Context context) {
        mContext = context;
        mZhuangbiItemList = new ArrayList<>();
    }

    public void setZhuangbiItemList(List<ZhuangbiItem> zhuangbiItemList) {
        mZhuangbiItemList = zhuangbiItemList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ZhuangbiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        TextView textView = new TextView(mContext);
        return new ZhuangbiHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ZhuangbiHolder holder, int position) {
        ZhuangbiItem galleryItem = mZhuangbiItemList.get(position);
        holder.bindGalleryItem(galleryItem);
    }

    @Override
    public int getItemCount() {
        return mZhuangbiItemList.size();
    }
}
