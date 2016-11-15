package com.sds.study.theadapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 자바의 그래픽 처리와 마찬가지로, 안드로이드에서 그래픽의 주체는 컴포넌트 즉 뷰이다!!
 * 따라서 개발자가 그래픽을 재정의 하려면, 뷰를 상속받아 뷰가 보유한 메서드 중 onDraw()
 * 메서드를 재정의 하면 된다..
 */

public class MyView extends View{
    Paint paint;
    RectF rectF;
    int x=0;
    int y=0;
    int left=100;
    int bottom=100;

    public MyView(Context context) {
        super(context);
        init();
    }

    //xml에서 호출할것을 대비해서 한것뿐이다...
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        this.setBackgroundColor(Color.YELLOW);
        paint = new Paint();
        paint.setColor(Color.RED);

    }

    //Canvas가 우리가 알던 그 Canvas 아니다.. 자바의 Graphics역할과 비슷....
    protected void onDraw(Canvas canvas) {
      /*  canvas.drawOval(0,0,100,100,paint);*/
        rectF = new RectF(x,y,left+x,bottom+y);
        canvas.drawOval(rectF,paint);
    }
}
