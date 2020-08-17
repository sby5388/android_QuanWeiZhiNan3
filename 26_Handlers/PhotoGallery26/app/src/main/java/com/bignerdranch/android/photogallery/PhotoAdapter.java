package com.bignerdranch.android.photogallery;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * TODO 20200428  出现了  显示不匹配的现象
 * fixme
 *
 * @author Administrator  on 2020/4/28.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.BaseHolder> {
    private static final int TYPE_EMPTY = 1;
    private static final int TYPE_NORMAL = 0;

    private List<GalleryItem> mGalleryItems = null;
    private Callback mCallback;

    public PhotoAdapter() {
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void setGalleryItems(List<GalleryItem> galleryItems) {
        mGalleryItems = galleryItems;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mGalleryItems == null) {
            return TYPE_EMPTY;
        }
        return TYPE_NORMAL;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.list_item_empty, parent, false);
            return new EmptyHolder(view);
        }
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_gallery, parent, false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseHolder photoHolder, int position) {
        if (photoHolder.isEmpty()) {
            return;
        }
        GalleryItem galleryItem = mGalleryItems.get(position);
        photoHolder.mItemImageView.setImageResource(R.drawable.bill_up_close);
        // TODO: 2020/4/28
        if (mCallback != null) {
            mCallback.bindData(photoHolder, galleryItem.getUrl());
        }
    }

    @Override
    public int getItemCount() {
        if (mGalleryItems == null) {
            return 1;
        }
        return mGalleryItems.size();
    }

    public static class EmptyHolder extends BaseHolder {

        public EmptyHolder(View itemView) {
            super(itemView);
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    }

    public static abstract class BaseHolder extends RecyclerView.ViewHolder {
        public ImageView mItemImageView;

        public BaseHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract boolean isEmpty();

        public void bindDrawable(Drawable drawable) {
            if (mItemImageView == null) {
                return;
            }
            mItemImageView.setImageDrawable(drawable);
        }
    }


    public static class PhotoHolder extends BaseHolder {

        public PhotoHolder(View itemView) {
            super(itemView);
            mItemImageView = (ImageView) itemView.findViewById(R.id.item_image_view);
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }

    public interface Callback {
        void bindData(BaseHolder photoHolder, String url);
    }
}
