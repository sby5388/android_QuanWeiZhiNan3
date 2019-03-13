package com.by5388.photogallery.zhuang.bi;

import com.by5388.photogallery.zhuang.bi.bean.QueryResult;

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
