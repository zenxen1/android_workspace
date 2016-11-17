package com.sds.study.graphicapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by student on 2016-11-16.
 */

public class Button extends GameObject {
    public Button(int x, int y, int width, int height, int color, Bitmap bitmap) {
        super(x, y, width, height, color, bitmap);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(bitmap,x,y,null);
    }
}
