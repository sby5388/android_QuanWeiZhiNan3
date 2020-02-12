package com.android.copy.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.copy.view.R;

/**
 * @author Administrator  on 2019/6/17.
 */
public class HToolsFragment extends ToolsFragment {
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.v_tools_fragments;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ToolsAdapter(this);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return getView().findViewById(R.id.recycler_view);
    }
}
