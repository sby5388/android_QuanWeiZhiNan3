package com.bignerdranch.android.photogallery.v2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.photogallery.GalleryItem;
import com.bignerdranch.android.photogallery.PhotoAdapter;
import com.bignerdranch.android.photogallery.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator  on 2020/4/28.
 */
public class PhotoGalleryFragment2 extends Fragment implements IGalleryTool.Callback, PhotoAdapter.Callback {
    private static final String TAG = "PhotoGalleryFragment2";
    private IGalleryTool mTool;

    private RecyclerView mPhotoRecyclerView;
    private List<GalleryItem> mItems = new ArrayList<>();
    private PhotoAdapter mAdapter;

    public static Fragment newInstance() {
        return new PhotoGalleryFragment2();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new PhotoAdapter();
        mAdapter.setCallback(this);
        mTool = new ThumbnailDownloader2(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPhotoRecyclerView = (RecyclerView) view.findViewById(R.id.photo_recycler_view);
        final View emptyView = view.findViewById(android.R.id.empty);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mPhotoRecyclerView.setAdapter(mAdapter);
//        mPhotoRecyclerView.empty
        mTool.loadImage();
    }

    @Override
    public void onLoadFinish(List<GalleryItem> list) {
        // TODO: 2020/4/28 需要优化
        mAdapter.setGalleryItems(list);

    }

    @Override
    public void bindData(PhotoAdapter.BaseHolder photoHolder, String url) {
        // TODO: 2020/4/28 绑定数据
        mTool.bindData(photoHolder, url);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTool.onDestroy();
    }
}
