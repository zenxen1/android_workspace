package com.sds.study.theadapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

/**
 * Created by student on 2016-11-15.
 */

public class GraphicActivity extends Activity implements View.OnClickListener,Runnable{
    MyView myView;
    Thread thread;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView= new MyView(this);


        //전체레이아웃
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        Button bt = new Button(this);
        bt.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        bt.setText("누르시오");

        layout.addView(bt);
        layout.addView(myView);


        setContentView(layout);
        bt.setOnClickListener(this);
    handler = new Handler(){
        //핸들러의 메서드는 메인 스레드에 의해 동작한다. 따라서 UI 제어가 가능하다.
        @Override
        public void handleMessage(Message msg) {
            myView.x+=10;
            myView.y+=10;
            myView.invalidate();
        }
    };


}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(this,"나건드렸어",Toast.LENGTH_SHORT).show();

        //빨간사각형을 움직여 보자!!
        myView.x+=10;
        myView.y+=10;
        myView.invalidate();

        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View view) {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true){
            try {
                thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        handler.sendEmptyMessage(0);
        }

    }
}
