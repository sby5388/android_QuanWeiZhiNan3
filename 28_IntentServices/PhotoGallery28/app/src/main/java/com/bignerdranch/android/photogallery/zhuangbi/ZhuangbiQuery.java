package com.bignerdranch.android.photogallery.zhuangbi;

import android.net.Uri;
import android.util.Log;

import com.bignerdranch.android.photogallery.zhuangbi.bean.QueryResult;

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
 * 后台查询：
 * 1.打开链接，获取jsonObject数据
 * 2.解析json数据,生成对象
 * 3.返回结果
 *
 * @author Administrator
 */
public class ZhuangbiQuery {

    private static final String TAG = "ZhuangbiQuery";

    /**
     * 解析url，获取内容byte[]
     */
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        System.out.println("getUrlBytes");
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
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
            //TODO 如何把流转换成文件:断点下载，多线程下载
            //需要相应的测试类来验证
            in.close();
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        //编码过的
        System.out.println("getUrlString 编码后 = " + urlSpec);
        //解码
        String decode = Uri.decode(urlSpec);
        System.out.println("getUrlString 解码后 = " + decode);
        //编码
        String encode = Uri.encode(decode);
        System.out.println("getUrlString 编码后 = " + encode);
        return new String(getUrlBytes(urlSpec));
    }

    public List<QueryResult> getDefault() {
        final String defaultValue = "装逼";
        return fetchItems(defaultValue);
    }

    public List<QueryResult> fetchItems(String queryWord) {
        System.out.println("fetchItems = ");
        List<QueryResult> items = new ArrayList<>();

        try {
            //查询的网址
            String url = Uri.parse("https://www.zhuangbi.info/search/")
                    .buildUpon()
                    //查询的参数
                    .appendQueryParameter("q", queryWord)
                    .build()
                    .toString();
            Log.d(TAG, "fetchItems: " + url);
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONArray jsonArray = new JSONArray(jsonString);
            parseItems(items, jsonArray);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return items;
    }

    private void parseItems(List<QueryResult> items, JSONArray jsonArray)
            throws IOException, JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject zbJsonObject = jsonArray.getJSONObject(i);
            QueryResult item = new QueryResult();
            item.setId(zbJsonObject.getInt("id"));
            item.setDescription(zbJsonObject.getString("description"));
            item.setPath(zbJsonObject.getString("path"));
            item.setSize(zbJsonObject.getInt("size"));
            item.setWidth(zbJsonObject.getInt("width"));
            item.setImageUrl(zbJsonObject.getString("image_url"));
            item.setFileSize(zbJsonObject.getString("file_size"));
            items.add(item);
        }
    }

}
