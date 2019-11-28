package com.bignerdranch.android.criminalintent.temp;

/**
 * @author Administrator  on 2019/10/22.
 */
public class TempCrime {

    private String mTitle;
    private String mDate;


    public TempCrime(String title, String date) {
        mTitle = title;
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }
}
