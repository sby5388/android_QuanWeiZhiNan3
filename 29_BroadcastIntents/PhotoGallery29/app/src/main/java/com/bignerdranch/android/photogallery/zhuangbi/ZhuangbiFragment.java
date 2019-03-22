package com.bignerdranch.android.photogallery.zhuangbi;

import android.content.Context;
import android.content.Intent;
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
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


    private ZbDownloadHandlerThread<ZhuangbiHolder> mHandlerThread;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                ZbQueryPreferences.setStoredQuery(getContext(), null);
                updateItems();
                return true;
            case R.id.menu_item_toggle_polling:
                boolean shouldStartAlarm = !ZhuangbiService.isServiceAlarmOn(getContext());
                ZhuangbiService.setServiceAlarm(getActivity(), shouldStartAlarm);
                getActivity().invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void updateItems() {
        String query = ZbQueryPreferences.getStoredQuery(getActivity());
        if (!TextUtils.isEmpty(query)) {
            new ZhuangbiTask(this).execute(query);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO
        setRetainInstance(true);
        setHasOptionsMenu(true);
        initData();

        Context context = getContext();
        if (context != null) {
            Intent intent = ZhuangbiService.newIntent(context);
            context.startService(intent);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_photo_gallery, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //是否按下回车键时就查询
                ZbQueryPreferences.setStoredQuery(getContext(), s);
                updateItems();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //是否在文本发生改变时就查询
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = ZbQueryPreferences.getStoredQuery(getActivity());
                searchView.setQuery(query, false);
            }
        });
        MenuItem toggleItem = menu.findItem(R.id.menu_item_toggle_polling);
        if (ZhuangbiService.isServiceAlarmOn(getActivity())) {
            toggleItem.setTitle(R.string.stop_polling);
        } else {
            toggleItem.setTitle(R.string.start_polling);
        }
    }

    private void initData() {
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
        mAdapter = new ZhuangbiAdapter(getContext(), mHandlerThread);
        // TODO: 2019/3/13 使用 Picasso进行加载
//        mAdapter = new PicassoAdapter(getContext(), mHandlerThread);
        // TODO: 2019/3/13 使用Glide
//        mAdapter = new GlideAdapter(getContext(), mHandlerThread);
    }

    public static Fragment newInstance() {
        return new ZhuangbiFragment();
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
        // TODO: 2019/3/13 使用HandlerThread 实现下载
        updateItems();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandlerThread.clearQueue();
        mHandlerThread.quit();

        Log.d(TAG, "onDestroy: mHandlerThread.quit()");
    }
}
