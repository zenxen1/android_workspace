package com.sds.study.graphicapp;


 /*개발자가 주도하여 뷰의 그림을 그려야 한다...onDraw메서드 재정의해야 한다..
 javaSE의 Paint와 비슷함
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View{
    private Bitmap bitmap;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(bitmap!=null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
