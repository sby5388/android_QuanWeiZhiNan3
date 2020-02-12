package com.by5388.gallery;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.by5388.gallery.bean.Photo;
import com.by5388.gallery.bean.PhotoList;
import com.by5388.gallery.bean.Result;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class FlickrFetchr {
    //TODO
    private static final String API_KEY = BuildConfig.FLICKR_API_KEY;
    private static final String TAG = "FlickrFetchr";
    private static final boolean USE_GSON = true;
    private final static Result sResult = new Result();
    private Gson mGson = new Gson();
    private static final String FETCH_RECENTS_METHOD = "flickr.photos.getRecent";
    private static final String SEARCH_METHOD = "flickr.photos.search";
    private static final String BASE_FLICKR_API = "https://api.flickr.com/services/rest/";
    // TODO: 2019/12/13 通用的请求参数
    private static final Uri BASE_URL = Uri.parse(BASE_FLICKR_API)
            .buildUpon()
            .appendQueryParameter("api_key", API_KEY)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .appendQueryParameter("extras", "url_s")
            .build();


    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // TODO: 2019/12/11 method:Get时getInputStream，method:Push 时getOutputStream时，
            //  才去真正连接网络，打开Url
            final InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    /**
     * 搜索
     *
     * @param query 关键字
     */
    public List<GalleryItem> searchPhotos(String query) {
        final Uri.Builder uriBuilder = BASE_URL.buildUpon()
                .appendQueryParameter("method", SEARCH_METHOD);
        uriBuilder.appendQueryParameter("text", query);
        final Uri uri = uriBuilder.build();
        return downloadGalleryItems(uri.toString());
    }

    public List<GalleryItem> fetchItems(final int page) {
        final Uri uri = BASE_URL.buildUpon()
                .appendQueryParameter("method", FETCH_RECENTS_METHOD)
                .appendQueryParameter("page", String.valueOf(page))
                .build();
        return downloadGalleryItems(uri.toString());
    }


    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }
    
    private void parseItems(List<GalleryItem> items, JSONObject jsonBody)
            throws IOException, JSONException {
        // TODO: 2019/12/11 这么做的前提是要知道返回结果的数据的数据结构
        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");

        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);

            GalleryItem item = new GalleryItem();
            item.setId(photoJsonObject.getString("id"));
            item.setCaption(photoJsonObject.getString("title"));

            if (!photoJsonObject.has("url_s")) {
                continue;
            }

            item.setUrl(photoJsonObject.getString("url_s"));
            items.add(item);
        }
    }

    // TODO: 2019/12/12 use mGson handle result
    private void parseItemsByGson(final List<GalleryItem> items, final String resultBody) {
        try {
            final Result result = mGson.fromJson(resultBody, sResult.getClass());
            if (result == null) {
                return;
            }
            final PhotoList photoList = result.mPhotoList;
            if (photoList == null) {
                return;
            }
            final List<Photo> photos = photoList.mPhoto;
            if (photos == null || photos.isEmpty()) {
                return;
            }
            for (Photo photo : photos) {
                if (photo == null) {
                    continue;
                }
                final GalleryItem item = new GalleryItem();
                item.setId(photo.mId);
                item.setCaption(photo.mTitle);
                item.setUrl(photo.mUrl);
                items.add(item);
            }

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "parseItemsByGson: finish");
    }

    private Uri.Builder createBaseUriBuilder() {
        return Uri.parse("https://api.flickr.com/services/rest/")
                .buildUpon()
                .appendQueryParameter("method", "flickr.photos.getRecent")
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .appendQueryParameter("extras", "url_s")
                //每页的数量
                .appendQueryParameter("perpage", String.valueOf(10));
    }


    private String buildUrl(final String method, String query) {
        final Uri.Builder uriBuilder = BASE_URL.buildUpon()
                .appendQueryParameter("method", method);
        if (SEARCH_METHOD.equals(method)) {
            uriBuilder.appendQueryParameter("text", query);
        }
        return uriBuilder.build().toString();
    }


    private List<GalleryItem> downloadGalleryItems(final String url) {
        List<GalleryItem> items = new ArrayList<>();
        try {
            //Log.d(TAG, "fetchItems: url = " + url);
            final String jsonString = getUrlString(url);
            // TODO: 2019/12/12 use Gson 练习25.10
            if (USE_GSON) {
                parseItemsByGson(items, jsonString);
            } else {
                Log.i(TAG, "Received JSON: " + jsonString);
                JSONObject jsonBody = new JSONObject(jsonString);
                parseItems(items, jsonBody);
            }
        } catch (IOException ioe) {
            // TODO: 2019/12/12 this Run On workerThread
            final GalleryApplication application = GalleryApplication.getApplication();
            application.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(application, "无法翻墙", Toast.LENGTH_SHORT).show();
                }
            });
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return items;
    }

}
