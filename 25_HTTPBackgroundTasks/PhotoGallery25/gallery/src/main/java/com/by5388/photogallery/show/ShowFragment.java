package com.by5388.photogallery.show;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.by5388.photogallery.FlickrFetchr;
import com.by5388.photogallery.R;
import com.by5388.photogallery.rx.api.NetTools;
import com.by5388.photogallery.rx.api.QueryService;
import com.by5388.photogallery.rx.bean.Result;
import com.by5388.photogallery.use.loader.ShowItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author admin  on 2019/2/13.
 */
public class ShowFragment extends Fragment {
    private static final String TAG = "PhotoGalleryFragment";

    private RecyclerView mPhotoRecyclerView;
    private ShowAdapter mShowAdapter;
    private Disposable mDisposable;

    public static ShowFragment newInstance() {
        return new ShowFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO
        setRetainInstance(true);
        mShowAdapter = new ShowAdapter(getContext());
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        mPhotoRecyclerView = (RecyclerView) v.findViewById(R.id.photo_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mPhotoRecyclerView.setAdapter(mShowAdapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NetTools netTools = new NetTools();
        QueryService service = netTools.getRetrofit().create(QueryService.class);
        mDisposable = service.getPhotos("flickr.photos.getRecent",
                FlickrFetchr.API_KEY,
                "json",
                "1",
                "url_s")
//                .timeout(10, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        List<ShowItem> items = new ArrayList<>();
                        items.addAll(result.getPhotos().getPhoto());
                        mShowAdapter.setGalleryItems(items);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof TimeoutException) {
                            Toast.makeText(getContext(), "超时", Toast.LENGTH_SHORT).show();
                            mDisposable.dispose();
                        }
                        Log.e(TAG, "accept: ", throwable);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
