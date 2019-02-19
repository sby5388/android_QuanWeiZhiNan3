package com.by5388.photogallery;

import com.by5388.photogallery.FlickrFetchr;
import com.by5388.photogallery.GalleryItem;

import org.junit.Test;

import java.util.List;

/**
 * @author admin  on 2019/2/13.
 */
public class TestKey {
    @Test
    public void testKey() {
        FlickrFetchr fickrFetchr = new FlickrFetchr();
        List<GalleryItem> galleryItems = fickrFetchr.fetchItems();
        for (GalleryItem galleryItem : galleryItems) {
            System.out.println(galleryItem.getCaption());
        }
    }
    //
    //com.by5388.photogallery
}
