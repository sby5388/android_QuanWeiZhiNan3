package com.by5388.photogallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author admin  on 2019/2/13.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
    private Context mContext;
    private List<GalleryItem> mGalleryItems;

    PhotoAdapter(Context context, List<GalleryItem> galleryItems) {
        mContext = context;
        mGalleryItems = galleryItems;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        TextView textView = new TextView(mContext);
        return new PhotoHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder photoHolder, int position) {
        GalleryItem galleryItem = mGalleryItems.get(position);
        photoHolder.bindGalleryItem(galleryItem);
    }

    @Override
    public int getItemCount() {
        return mGalleryItems.size();
    }
}
