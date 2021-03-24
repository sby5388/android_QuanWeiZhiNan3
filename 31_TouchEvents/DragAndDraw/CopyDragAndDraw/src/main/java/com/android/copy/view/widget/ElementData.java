package com.android.copy.view.widget;

import java.util.Random;

/**
 * @author Administrator  on 2019/6/17.
 */
class ElementData {
    private static Random random = new Random();
    int drawableID;
    int x;
    int y;

    ElementData(int drawableID, int width, int height) {
        this.drawableID = drawableID;
        x = random.nextInt(width);
        y = random.nextInt(height);
    }


}
