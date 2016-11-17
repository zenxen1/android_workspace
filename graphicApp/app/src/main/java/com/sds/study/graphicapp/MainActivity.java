package com.sds.study.graphicapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    String TAG;
    Bitmap[] bitmaps;
    static final int REQUEST_READ_PERMISSION=1;
    MyView myView;
    int current;
    /*개발자 정의 스레드는 즉 메인쓰레드 아닌 쓰레드는 UI제어할수 없으므로, 핸드러에게 UI처리를 부탁하자!*/
    Handler handler;
    Thread thread;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=this.getClass().getName();
        setContentView(R.layout.activity_main);
        myView = (MyView)findViewById(R.id.myview);

        handler = new Handler(){
            public void handleMessage(Message msg) {
                /*동생 쓰레드들의 부탁을 여기서 처리하자*/
                current++;
                showImage();

            }
        };

        /*퍼미션확인*/
        int readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(readPermission== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },REQUEST_READ_PERMISSION);
        }else{
            init();
        }
     }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_READ_PERMISSION :
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    init();
                }
        }
    }

    /*갤러리에 필요한 초기화 작업을 여기서 하자!!*/
    public void init(){
        File dir = new File(Environment.getExternalStorageDirectory(),"iot_images");
        File[] files = dir.listFiles();
        bitmaps = new Bitmap[files.length];
        for(int i=0;i<files.length;i++){
            bitmaps[i]= BitmapFactory.decodeFile(files[i].getAbsolutePath());
            Log.d(TAG,files[i]+"경로로 생성된 객체는"+bitmaps[i]);
        }

        /*myView.setBitmap(bitmaps[0]);
        myView.invalidate();*/
        showImage();

    }
    public void prev(View view){
        current--;
        showImage();
    }
    public void auto(View view){
        thread = new Thread(){
            public void run() {
                /*부탁*/
                while (true) {
                    try {
                        thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(0);
                }
            }
        };
        thread.start();
    }
    public void next(View view){
        current++;
        showImage();
    }
    public void showImage(){
        if(current>bitmaps.length-1){
            current=0;
        }
        myView.setBitmap(bitmaps[current]);
        myView.invalidate();

    }
}
