package com.by5388.photogallery.zhuang.bi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.by5388.photogallery.R;
import com.by5388.photogallery.zhuang.bi.bean.QueryResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin  on 2019/3/13.
 */
public class ZhuangbiFragment extends Fragment implements QueryCallBack {

    private static final String TAG = "ZhuangbiFragment";

    private ZhuangbiAdapter mAdapter;

    private ZhuangbiTask mTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO
        setRetainInstance(true);
        mAdapter = new ZhuangbiAdapter(getContext());
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
        // TODO: 2019/3/13 getData
        if (mTask == null) {
            mTask = new ZhuangbiTask(this);
            String queryWord = "金馆长";
            mTask.execute(queryWord);
        }


    }

}
