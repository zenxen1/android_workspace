package com.sds.study.graphicapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by student on 2016-11-16.
 */

public class GameView extends View implements Runnable{
    String TAG;
    /*Bullet bullet;*/
    ObjectManager objectManager;
    Thread thread;/*게임을 운영할 엔진(심장박동)*/
    Handler handler; /*쓰레드가 UI를 제어할 수 있도록...*/
    int[] color={
            Color.BLACK,
            Color.GRAY,
            Color.CYAN,
            Color.DKGRAY,
            Color.MAGENTA,
            Color.LTGRAY
    };

    Bitmap[] bitmaps = new Bitmap[3];
  /*  Paint paint;
    RectF rectF;
    int x=0;
    int y=0;
    int right=50;
    int bottom=50;*/

    public GameView(Context context) {
        super(context);
        TAG = this.getClass().getName();
        this.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(Color.YELLOW);
      /*  paint = new Paint();
        paint.setColor(Color.RED);
        rectF = new RectF(x,y,right,bottom);*/
        init();
        /*총알로 사용될 이미지를 준비해놓자*/
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=4;
        Bitmap temp= BitmapFactory.decodeResource(getResources(),R.drawable.bullet,options);
        bitmaps[0]=Bitmap.createBitmap(temp,0,0,30,30);

        /*총알발사 버튼 등장*/
        bitmaps[1]=BitmapFactory.decodeResource(getResources(),R.drawable.button);
        Button btn = new Button(1000,500,100,100,color[1],bitmaps[1]);
        objectManager.addObject(btn);
    }

    /*게임 시작 직전에 해줘야할 업무를 여기서 처리하자!*/
    public void init(){
        handler = new Handler(){
            /*메인쓰레드에 의해 아래의 메서드가 호출 되므로 결국 UI제어가 가능하다..*/
            public void handleMessage(Message msg) {
                /*모든 오브젝트의 tick,render*/
                tick();
                invalidate();
            }
        };
        thread = new Thread(this);
        thread.start();

        objectManager = new ObjectManager();
    }

    public void tick(){
        objectManager.tick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
      /*  canvas.drawOval(rectF,paint);*/
        /*if(bullet!=null) {
            bullet.render(canvas);
        }*/
        objectManager.render(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"뷰호출");
   /*     x+=5;
        y+=2;
        rectF.set(x,y,x+right,y+bottom);*/
        /*게임 오브젝트 생성하여 동작시키자*/
        Random random = new Random();
        int r = random.nextInt(color.length);
        Bullet bullet = new Bullet(0,200,50,50,color[r],bitmaps[0]);
        objectManager.addObject(bullet);
        /*this.invalidate();*/

        /*화면을 터치한 좌표얻어오기*/
        float posX = event.getX();
        float posY = event.getY();
        Toast.makeText(getContext(),"x:"+posX+"y"+posY,Toast.LENGTH_SHORT).show();
       return true;
    }

    @Override
    public void run() {
        while (true){
            try {
                thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //모든 게임 오브젝트들의 tick(),render();
            handler.sendEmptyMessage(0);
        }
    }
}
