package com.by5388.photogallery.rx.api;

import com.by5388.photogallery.FlickrFetchr;
import com.by5388.photogallery.rx.bean.Result;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;

/**
 * @author admin  on 2019/3/12.
 */
public class QueryServiceTest {
    private QueryService mSubject;

    @Before
    public void setUp() throws Exception {
        NetTools netTools = new NetTools();
        Retrofit retrofit = netTools.getRetrofit();
        mSubject = retrofit.create(QueryService.class);

    }

    @Test
    public void getPhotos() {
        Calendar calendar = Calendar.getInstance();
        final long time = calendar.getTimeInMillis();
        System.out.println(time);

        System.out.println(mSubject == null);
        mSubject.getPhotos(
                "flickr.photos.getRecent",
                FlickrFetchr.API_KEY,
                "json",
                "1"
                ,
                "url_s"
        ).subscribe(new Consumer<Result>() {
            @Override
            public void accept(Result result) throws Exception {
                System.out.println(result);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.err.println(throwable);
                Calendar calendar = Calendar.getInstance();
                long newTime = calendar.getTimeInMillis() - time;
                System.out.println(newTime / 1000);
            }
        });

    }
}