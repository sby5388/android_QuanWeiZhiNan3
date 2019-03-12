package com.by5388.photogallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by5388.photogallery.use.loader.ShowItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin  on 2019/2/13.
 */
public class ShowAdapter extends RecyclerView.Adapter<ShowHolder> {
    private Context mContext;
    private List<ShowItem> mGalleryItems;

    ShowAdapter(Context context) {
        mContext = context;
        mGalleryItems = new ArrayList<>();
    }

    public void setGalleryItems(List<ShowItem> galleryItems) {
        mGalleryItems = galleryItems;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        TextView textView = new TextView(mContext);
        return new ShowHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowHolder photoHolder, int position) {
        ShowItem galleryItem = mGalleryItems.get(position);
        photoHolder.bindGalleryItem(galleryItem);
    }

    @Override
    public int getItemCount() {
        return mGalleryItems.size();
    }
}
