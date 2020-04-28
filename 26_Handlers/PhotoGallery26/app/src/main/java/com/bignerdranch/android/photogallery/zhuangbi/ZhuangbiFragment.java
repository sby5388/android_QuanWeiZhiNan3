package com.bignerdranch.android.photogallery.zhuangbi;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.photogallery.R;
import com.bignerdranch.android.photogallery.zhuangbi.bean.QueryResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin  on 2019/3/13.
 */
public class ZhuangbiFragment extends Fragment implements QueryCallBack {

    private static final String TAG = "ZhuangbiFragment";

    private ZhuangbiAdapter mAdapter;

    private ZhuangbiTask mTask;

    private ZbDownloadHandlerThread<ZhuangbiHolder> mHandlerThread;

    public static Fragment newInstance() {
        return new ZhuangbiFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO
        setRetainInstance(true);

        // TODO: 2019/3/13 初始化下载工具:实现按需下载？
        Handler responseHandler = new Handler();

        mHandlerThread = new ZbDownloadHandlerThread<>(responseHandler);
        mHandlerThread.setZbThumbnailDownloadListener(
                new ZbDownloadHandlerThread.ZbThumbnailDownloadListener<ZhuangbiHolder>() {
                    @Override
                    public void onThumbnailDownloaded(ZhuangbiHolder target, Bitmap thumbnail) {
                        Drawable drawable = new BitmapDrawable(thumbnail);
                        target.bindZhuangbiItem(drawable);
                    }
                });

        mHandlerThread.start();
        mHandlerThread.getLooper();
        Log.d(TAG, "onCreate: mHandlerThread.start()");
        // TODO: 2019/3/13 使用ZbDownloadHandlerThread 进行加载
        if (true) {
            mAdapter = new ZhuangbiAdapter(getContext(), mHandlerThread);
        } else {
            // TODO: 2019/3/13 使用 Picasso进行加载
            mAdapter = new PicassoAdapter(getContext(), mHandlerThread);
        }

    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.photo_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(mAdapter);
        return v;
    }

    @Override
    public void update(List<QueryResult> results) {
        mAdapter.setZhuangbiItemList(new ArrayList<ZhuangbiItem>(results));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 2019/3/13 getData
        if (mTask == null) {
            mTask = new ZhuangbiTask(this);
            String queryWord = "金馆长";
            mTask.execute(queryWord);
        }
        // TODO: 2019/3/13 使用HandlerThread 实现下载


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandlerThread.clearQueue();
        mHandlerThread.quit();

        Log.d(TAG, "onDestroy: mHandlerThread.quit()");
    }
}
