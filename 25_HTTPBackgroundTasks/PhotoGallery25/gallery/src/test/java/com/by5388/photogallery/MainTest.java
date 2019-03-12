package com.by5388.photogallery;

import java.util.List;

/**
 * @author admin  on 2019/3/12.
 */
public class MainTest {
    public static void main(String[] args) throws Exception {
        final FlickrFetchr fetchr = new FlickrFetchr();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<GalleryItem> galleryItems = fetchr.fetchItems();
                System.out.println(galleryItems.size());
            }
        }).start();
        Thread.sleep(10000);
    }
}
