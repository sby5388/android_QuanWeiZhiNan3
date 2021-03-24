package com.android.alarmmanager2;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator  on 2019/4/3.
 */
public class O0oTest {

    private O0o mSubject;
    private List<Man> mList;

    @Before
    public void setUp() throws Exception {
        mSubject = new O0o();
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(new Man(i, "name" + i));
        }
    }

    @Test
    public void getMan() throws Exception {
        List<Man> manList = mSubject.getMan();
        for (Man man:manList){
            System.out.println(man);
        }
    }

    @Test
    public void write() throws Exception {
        mSubject.write(mList);
    }
}