package com.sds.study.graphicapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by student on 2016-11-16.
 */

public class Bullet extends GameObject {


    public Bullet(int x, int y, int width, int height, int color,Bitmap bitmap) {
        super(x, y, width, height, color,bitmap);
        velX=2;
    }

    @Override
    public void tick() {
        /*velX+=2;
        x=velX;*/
        x+=velX;
    }

    @Override
    public void render(Canvas canvas) {
        rectF.set(x,y,x+width,y+height);
        //canvas.drawOval(rectF,paint);
        canvas.drawBitmap(bitmap,x,y,null);
    }
}
