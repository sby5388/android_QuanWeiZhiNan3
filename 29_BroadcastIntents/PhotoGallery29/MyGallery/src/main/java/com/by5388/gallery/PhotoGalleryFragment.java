package com.by5388.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;


/**
 * @author Administrator
 */
public class PhotoGalleryFragment extends VisibleFragment {

    private static final String TAG = "PhotoGalleryFragment";
    private RecyclerView mPhotoRecyclerView;
    private PhotoAdapter mAdapter;
    private int mPage = 1;
    // TODO: 2019/12/12 防止重复加载
    private boolean mLoading = false;
    private GridLayoutManager mGridLayoutManager;
    private ThumbnailDownloader<PhotoAdapter.PhotoHolder> mDownloader;
    private InputMethodManager mInputMethodManager;


    private final ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            // TODO: 2019/12/12 skip
            if (true) {
                return;
            }
            final int width = mPhotoRecyclerView.getWidth();
            Log.d(TAG, "getSpanCount: width = " + width);
            final View view = LayoutInflater.from(getContext()).inflate(R.layout.item_gallery, null);
            final int count = width / 300;
            Log.d(TAG, "getSpanCount: count = " + count);
            mGridLayoutManager.setSpanCount(count);
        }
    };

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 2019/12/12 旋转屏幕后数据丢失
        setRetainInstance(true);
        // TODO: 2019/12/13 add Menu
        setHasOptionsMenu(true);

        mDownloader = new ThumbnailDownloader<>(new Handler(), new ThumbnailDownloader.ThumbnailDownloadListener<PhotoAdapter.PhotoHolder>() {
            @Override
            public void onThumbnailDownloaded(PhotoAdapter.PhotoHolder photoHolder, Bitmap bitmap) {
                photoHolder.bindGalleryItem(new BitmapDrawable(getResources(), bitmap));
            }
        });
        mDownloader.start();
        mDownloader.getLooper();
        // TODO: 2019/12/12 Fragment 销毁时，需要及时AsyncTask#cancel(),避免内存泄露
        updateItems();

        //final Context context = Objects.requireNonNull(getContext());
        //PollService.setServiceAlarm(context, true);
        // TODO: 2019/12/31 每次都被强制打开
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_photo_gallery, menu);
        final MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: query = " + query);
                QueryPreferences.setStoredQuery(query);
                updateItems();
                // TODO: 2019/12/13 移除焦点时，自动隐藏输入法
                searchView.clearFocus();
                // FIXME: 2019/12/13 只是隐藏掉输入法，并没有 关闭ActionView#SearchView
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO: 2019/12/13 文本发生变化就触发，不需要
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/12/13 显示上一次的值
                final String query = QueryPreferences.getStoredQuery();
                searchView.setQuery(query, false);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });

        // TODO: 2019/12/24 根据状态设置文本
        final MenuItem item = menu.findItem(R.id.menu_item_toggle_polling);
        final Context context = getContext();
        if (PollService.isServiceAlarmOn(context)) {
            item.setTitle(R.string.start_polling);
        } else {
            item.setTitle(R.string.stop_polling);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        if (R.id.menu_item_clear == itemId) {
            toast(R.string.clear);
            QueryPreferences.setStoredQuery("");
            updateItems();
            return true;
        }
        if (R.id.menu_item_search == itemId) {
            toast(R.string.search);
            return true;
        }
        if (R.id.menu_item_toggle_polling == itemId) {
            final Context context = Objects.requireNonNull(getContext());
            //当前的开启状态
            final boolean serviceAlarmOn = PollService.isServiceAlarmOn(context);
            //新的状态
            final boolean showStartAlarm = !serviceAlarmOn;
            PollService.setServiceAlarm(context, showStartAlarm);
            // TODO: 2019/12/24 强制刷新菜单
            Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateItems() {
        // TODO: 2019/12/13
        final String query = QueryPreferences.getStoredQuery();
        new FetchItemsTask(query).execute();
    }

    private void toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void toast(int id) {
        Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        mPhotoRecyclerView = (RecyclerView) v.findViewById(R.id.photo_recycler_view);
        // TODO: 2019/12/12 设置为StaggeredGridLayoutManager 类型的布局
        mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mPhotoRecyclerView.setLayoutManager(mGridLayoutManager);
        // TODO: 2019/12/12  when scroll to Top or Bottom to load other page 练习25.11
        mPhotoRecyclerView.addOnScrollListener(mOnScrollListener);
        mAdapter = new PhotoAdapter(mDownloader);
        mPhotoRecyclerView.setAdapter(mAdapter);
        // TODO: 2019/12/12 change layoutManager#SpanCount when view change 布局发生变化时调用
        mPhotoRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
        return v;
    }


    /**
     * 文档提示：销毁时需要remove this
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                return;
            }
            if (mLoading) {
                Toast.makeText(getContext(), "加载中", Toast.LENGTH_SHORT).show();
                return;
            }

            //停止
            if (mAdapter.isBottom()) {
                Toast.makeText(getContext(), "正在加载下一页", Toast.LENGTH_SHORT).show();
                mPage++;
                refreshData();
            } else if (mAdapter.isTop() && !mAdapter.isAppendMode()) {
                if (mPage > 1) {
                    mPage--;
                    Toast.makeText(getContext(), "正在加载上一页", Toast.LENGTH_SHORT).show();
                    refreshData();
                } else {
                    Toast.makeText(getContext(), "已是第一页", Toast.LENGTH_SHORT).show();
                }
            }

        }

        private void refreshData() {
            mLoading = true;
            new FetchItemsTask().execute(mPage);
        }

    };


    private class FetchItemsTask extends AsyncTask<Integer, Void, List<GalleryItem>> {
        // TODO: 2019/12/13 test queryPhoto
        private static final boolean TEST_SEARCH = false;
        private boolean mQueryMode = true;

        private String mQuery;

        FetchItemsTask(String query) {
            mQueryMode = true;
            mQuery = query;
        }

        FetchItemsTask() {
            mQueryMode = false;
        }

        @Override
        protected List<GalleryItem> doInBackground(Integer... integers) {
            if (mQueryMode) {
                if (TextUtils.isEmpty(mQuery)) {
                    return new FlickrFetchr().fetchItems(1);
                }
                return new FlickrFetchr().searchPhotos(mQuery);
            }
            if (TEST_SEARCH) {
                final String query = "cat";
                return new FlickrFetchr().searchPhotos(query);
            }
            return new FlickrFetchr().fetchItems(integers[0]);
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            mAdapter.setGalleryItems(items);
            if (!mAdapter.isAppendMode()) {
                mPhotoRecyclerView.scrollToPosition(0);
            }
            mLoading = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDownloader.clearQueue();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPhotoRecyclerView.removeOnScrollListener(mOnScrollListener);
        mDownloader.quit();
    }


    /**
     * TODO: 2019/12/12
     *
     * @return 动态计算GridLayoutManager 每一行的数量
     */
    private int getSpanCount() {
        if (true) {
            return 3;
        }
        // FIXME: 2019/12/12 基于RecyclerView的当前宽度和预定义网格列宽，
        //  就可以计算出列数。一直失败
        final int width = mPhotoRecyclerView.getWidth();
        Log.d(TAG, "getSpanCount: width = " + width);
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.item_gallery, null);
        final int count = width / 100;
        Log.d(TAG, "getSpanCount: count = " + count);
        return count;
    }


}
