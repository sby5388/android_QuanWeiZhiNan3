package com.bignerdranch.android.photogallery.zhuangbi;

import com.bignerdranch.android.photogallery.zhuangbi.bean.QueryResult;

import java.util.List;

/**
 * @author admin  on 2019/3/13.
 */
public interface QueryCallBack {
    /**
     * 结果回调
     *
     * @param results 查询结果
     */
    void update(List<QueryResult> results);
}
