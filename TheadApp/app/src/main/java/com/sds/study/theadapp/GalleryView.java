package com.sds.study.theadapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.util.jar.Manifest;

/**
 * Created by student on 2016-11-15.
 */

public class GalleryView extends View {
    Paint paint;
    Bitmap bitmap;

    public GalleryView(Context context,Bitmap bitmap) {
        super(context);
        this.bitmap = bitmap;
        init();
    }

    public void init(){
        this.setBackgroundColor(Color.YELLOW);
        paint= new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap,0,0,paint);
    }
}
