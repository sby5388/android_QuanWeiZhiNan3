package com.by5388.photogallery;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author admin  on 2019/3/12.
 */
@RunWith(AndroidJUnit4.class)
public class FlickrFetchrTest {
    private FlickrFetchr mSubject;

    @Before
    public void setUp() throws Exception {
        mSubject = new FlickrFetchr();
    }

    @Test
    public void fetchItems() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<GalleryItem> galleryItems = mSubject.fetchItems();
                System.out.println(galleryItems);
            }
        }).start();
        Thread.sleep(10000);

    }
}