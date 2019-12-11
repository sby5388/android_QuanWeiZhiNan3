package com.by5388.photogallery.rx.api;

import com.by5388.photogallery.rx.bean.Result;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author admin  on 2019/3/12.
 */
public interface QueryService {
    /**
     * 查询图片
     *
     * @param method
     * @param apiLey
     * @param format
     * @param callBack
     * @param extras
     * @return
     */
    @GET("services/rest")
    Observable<Result> getPhotos(@Query("method") String method,
                                 @Query("api_key") String apiLey,
                                 @Query("format") String format,
                                 @Query("nojsoncallback") String callBack,
                                 @Query("extras") String extras
    );
}
