package com.bignerdranch.android.mockwalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class MockWalkerFragment extends Fragment {
    @InjectView(R.id.start_button)
    CompoundButton mStartButton;
    // TODO: 2019/6/20 类似于rxJava2中的水管：Disposable/Com,用于关闭时阻止事件继续传递
    private final CompositeSubscription mServiceSubscriptions = new CompositeSubscription();
    private Intent mServiceIntent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mockwalker, container, false);

        ButterKnife.inject(this, v);

        mServiceIntent = MockWalkerService.newIntent(getContext());

        mServiceSubscriptions.add(
                MockWalker.getInstance()
                        .getChanges()
                        .subscribe(new Action1<MockWalker>() {
                            @Override
                            public void call(MockWalker mockWalker) {
                                updateUI(mockWalker);
                            }
                        }));

        updateUI(MockWalker.getInstance());

        return v;
    }

    @OnClick(R.id.start_button)
    public void onStartButtonClick() {
        MockWalker mockWalker = MockWalker.getInstance();

        if (mockWalker.isStarted()) {
            getActivity().stopService(mServiceIntent);
        } else {
            getActivity().startService(mServiceIntent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mServiceSubscriptions.unsubscribe();
    }

    private void updateUI(MockWalker mockWalker) {
//        MockWalker mockWalker = MockWalker.getInstance();
        if (mockWalker.isStarted()) {
            mStartButton.setChecked(true);
        } else {
            mStartButton.setChecked(false);
        }
    }
}
