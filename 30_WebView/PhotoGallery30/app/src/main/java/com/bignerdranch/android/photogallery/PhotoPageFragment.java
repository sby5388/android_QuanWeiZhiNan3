package com.bignerdranch.android.photogallery;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class PhotoPageFragment extends VisibleFragment {
    private static final String ARG_URI = "photo_page_url";

    private Uri mUri;
    private WebView mWebView;
    private ProgressBar mProgressBar;

    public static PhotoPageFragment newInstance(Uri uri) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_URI, uri);

        PhotoPageFragment fragment = new PhotoPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUri = getArguments().getParcelable(ARG_URI);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_page, container, false);

        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        // WebChromeClient reports in range 0-100
        mProgressBar.setMax(100);

        mWebView = (WebView) v.findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);

        //WebChromeClient()：那么WebChromeClient就是一个事件接口，用来响应
        //那些改变浏览器中装饰元素的事件。这包括JavaScript警告信息、网页图标、状态条加载，
        //以及当前网页标题的刷新。

        // TODO: 2019/3/22 设置加载时的进度回调
        mWebView.setWebChromeClient(new WebChromeClient() {
            // TODO: 2019/3/22 进度条
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }

            // TODO: 2019/3/22 标题：设置子标题(二级标题)
            @Override
            public void onReceivedTitle(WebView webView, String title) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                activity.getSupportActionBar().setSubtitle(title);
            }
        });
        //TODO WebViewClient：用来响应WebView上的渲染事件
        mWebView.setWebViewClient(new WebViewClient());
        // TODO: 2019/3/22 webView可以加载动态图
        mWebView.loadUrl(mUri.toString());

        return v;
    }

    private void test() {
        //WebChromeClient()：那么WebChromeClient就是一个事件接口，用来响应
        //那些改变浏览器中装饰元素的事件。这包括JavaScript警告信息、网页图标、状态条加载，
        //以及当前网页标题的刷新。

        WebChromeClient chromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
               //进度回调
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                //接收标题
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                //接收链接所对应的icon
            }

        };
    }

}
