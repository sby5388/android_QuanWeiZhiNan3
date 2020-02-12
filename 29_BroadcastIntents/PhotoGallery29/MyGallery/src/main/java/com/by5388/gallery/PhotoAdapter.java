package com.by5388.gallery;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator  on 2019/12/12.
 */

class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {
    // TODO: 2019/12/12 练习25.11 数据增加在后面，但这样会失去加载上一页的，且显示的数据过多消耗内存
    private final boolean mAppendMode = false;
    private Picasso.Builder mBuilder;
    private Drawable mDefaultDrawable;

    private final boolean mUsePicasso = false;

    public boolean isAppendMode() {
        return mAppendMode;
    }

    private List<GalleryItem> mGalleryItems;
    private int mPosition = 0;
    private final ThumbnailDownloader<PhotoHolder> mDownloader;

    public boolean isBottom() {
        return mPosition == mGalleryItems.size() - 1;
    }

    public boolean isTop() {
        return mPosition == 0;
    }

    PhotoAdapter(ThumbnailDownloader<PhotoHolder> downloader) {
        mGalleryItems = new ArrayList<>();
        this.mDownloader = downloader;
    }

    public void setGalleryItems(List<GalleryItem> galleryItems) {
        if (mAppendMode) {
            mGalleryItems.addAll(galleryItems);
        } else {
            mGalleryItems = galleryItems;
        }
        this.notifyDataSetChanged();

    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        final View view = inflater.inflate(R.layout.item_gallery, viewGroup, false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder photoHolder, int position) {
        final GalleryItem galleryItem = mGalleryItems.get(position);
        // TODO: 2019/12/12
        if (mDefaultDrawable == null) {
            mDefaultDrawable = GalleryApplication.getApplication().getResources().getDrawable(R.drawable.bill_up_close);
        }
        // TODO: 2019/12/13 需要设置图片占位符
        photoHolder.bindGalleryItem(mDefaultDrawable);

        mPosition = photoHolder.getAdapterPosition();
        // TODO: 2019/12/12 交给下载工具去下载图片
        if (mUsePicasso) {
        } else {
            mDownloader.queueThumbnail(photoHolder, galleryItem.getUrl());
        }


    }

    @Override
    public void onViewRecycled(@NonNull PhotoHolder holder) {
        super.onViewRecycled(holder);
        holder.mImageView.setImageDrawable(null);
    }

    @Override
    public int getItemCount() {
        return mGalleryItems.size();
    }

    class PhotoHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        PhotoHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_image_view);
        }

        void bindGalleryItem(Drawable drawable) {
            mImageView.setImageDrawable(drawable);
        }
    }

}