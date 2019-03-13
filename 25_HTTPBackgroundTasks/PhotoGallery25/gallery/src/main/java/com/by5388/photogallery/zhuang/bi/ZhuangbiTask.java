package com.by5388.photogallery.zhuang.bi;

import android.os.AsyncTask;

import com.by5388.photogallery.zhuang.bi.bean.QueryResult;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author admin  on 2019/3/13.
 */
public class ZhuangbiTask extends AsyncTask<String, Void, List<QueryResult>> {

    private WeakReference<QueryCallBack> mWeakReference;

    public ZhuangbiTask(QueryCallBack callBack) {
        mWeakReference = new WeakReference<>(callBack);
    }

    @Override
    protected void onPostExecute(List<QueryResult> queryResults) {
        QueryCallBack callBack = mWeakReference.get();
        if (callBack != null && queryResults != null) {
            callBack.update(queryResults);
        }
    }

    @Override
    protected List<QueryResult> doInBackground(String... strings) {
        // TODO: 2019/3/13
        return new ZhuangbiQuery().fetchItems(strings[0]);
    }
}
