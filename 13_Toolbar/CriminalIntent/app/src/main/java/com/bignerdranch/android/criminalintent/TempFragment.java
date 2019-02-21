package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;


/**
 * @author admin  on 2019/2/21.
 */
public class TempFragment extends Fragment {

    private static void getStringByNumber(Context context, int number) {
        Resources resources = context.getResources();
        // TODO: 2019/2/21 getQuantityString:根据数量的不同，返回不同的字符串
        //
        String quantityString = resources.getQuantityString(R.plurals.apple, number);
        System.out.println(quantityString);
    }
}
