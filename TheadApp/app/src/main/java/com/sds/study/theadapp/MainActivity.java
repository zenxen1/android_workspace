package com.sds.study.theadapp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Runnable{
    String TAG;
    Thread thread;
    int count;
    TextView txt_count;
    //안드로이드에서는 개발자가 정의한 쓰레드 즉 메인쓰레드 아닌자가 UI를 제어할 수 없다!!
    //해결책 - Handler에 의한 간접 제어 방식을 허용한다 개발자가 정의한 쓰레드로 UI를 제어하고 싶다면
    //Handler에 예약하면 된다.

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=this.getClass().getName();

        setContentView(R.layout.activity_main);
        txt_count = (TextView) findViewById(R.id.txt_count);
        handler = new Handler(){
            //핸들러에게 다른 객체가 업무를 부탁하면, 아래의 메서드가 동작하게 된다
            //우리의 경우 UI처리를 여기서 하면 된다.
            public void handleMessage(Message msg) {
                count++;
                txt_count.setText(Integer.toString(count));
            }
        };
    }
    public void btnClick(View view){
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while(true){
            try {
                thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*Log.d(TAG,"Count :"+count++);*/

            //핸들러에게 부탁하자!!
            //핸들 메세지가 호출 된다 ..
            handler.sendEmptyMessage(0);
        }
    }
}
