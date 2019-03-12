package com.by5388.photogallery;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * @author admin  on 2019/2/13.
 */
public class TestKey {
    private FlickrFetchr mSubject;

    @Before
    public void setup() throws Exception {
        mSubject = mock(FlickrFetchr.class);
    }

    @Test
    public void testKey() {
        //com.by5388.photogallery
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<GalleryItem> galleryItems = mSubject.fetchItems();
                System.out.println(galleryItems.size());
                for (GalleryItem galleryItem : galleryItems) {
                    System.out.println(galleryItem.getCaption());
                }
            }
        }).start();
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
