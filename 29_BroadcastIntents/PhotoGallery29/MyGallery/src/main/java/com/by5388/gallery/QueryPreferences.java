package com.by5388.gallery;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * 记录上一次搜索的
 *
 * @author Administrator  on 2019/12/13.
 */
public class QueryPreferences {
    private static final String TAG = QueryPreferences.class.getSimpleName();
    private static final String STORE_KEY = "store_query_key";
    private static final String LAST_RESULT_ID = "lastResultId";
    private static final String PREF_IS_ALARM_ON = "isAlarmOn";

    public static String getStoredQuery() {
        final String value = PreferenceManager.getDefaultSharedPreferences(GalleryApplication.getApplication())
                .getString(STORE_KEY, "");
        Log.d(TAG, "getStoredQuery: value = " + value);
        return value;
    }

    public static void setStoredQuery(final String query) {
        Log.d(TAG, "setStoredQuery: query = " + query);
        PreferenceManager.getDefaultSharedPreferences(GalleryApplication.getApplication())
                .edit()
                .putString(STORE_KEY, query)
                .apply();
    }

    public static String getLastResultId() {
        return PreferenceManager.getDefaultSharedPreferences(GalleryApplication.getApplication())
                .getString(LAST_RESULT_ID, null);
    }

    public static void setLastResultId(String id) {
        Log.d(TAG, "setLastResultId: id = " + id);
        PreferenceManager.getDefaultSharedPreferences(GalleryApplication.getApplication())
                .edit()
                .putString(LAST_RESULT_ID, id)
                .apply();

    }

    public static boolean isAlarmOn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_IS_ALARM_ON, false);
    }

    public static void setAlarmOn(Context context, boolean isOn) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_IS_ALARM_ON, isOn)
                .apply();
    }


}
