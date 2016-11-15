package com.sds.study.theadapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by student on 2016-11-15.
 */

public class GalleryActivity extends Activity implements Runnable{
    static final int REQUEST_GALLERY_PERMIITION=1;
    Bitmap bitmap;
    int count=0;
    GalleryView galleryView;
    Thread thread;
    Handler handler;
    File[] file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permission();

        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        galleryView = new GalleryView(this,bitmap);


        Button pre = new Button(this);
        pre.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        pre.setText("pre");
        Button play = new Button(this);
        play.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        play.setText("play");
        Button next = new Button(this);
        next.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        next.setText("next");

        layout.addView(pre);
        layout.addView(play);
        layout.addView(next);
        layout.addView(galleryView);
        setContentView(layout);

        pre.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if(count>0) {
                    count--;
                    openGallery();
                    galleryView.bitmap = bitmap;
                    galleryView.invalidate();
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thread = new Thread();
                thread.start();

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<file.length-1) {
                    count++;
                    openGallery();
                    galleryView.bitmap = bitmap;
                    galleryView.invalidate();
                }
            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                count++;
                if(count==8){
                    count=0;
                }
                openGallery();
                galleryView.bitmap = bitmap;
                galleryView.invalidate();
            }
        };

    }

    public void permission(){
        int readpermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(readpermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
            },REQUEST_GALLERY_PERMIITION);
        }else{
            openGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case REQUEST_GALLERY_PERMIITION:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openGallery();
                }
        }
    }

    public void openGallery(){
        File dir = Environment.getExternalStorageDirectory();
        File filelist = new File(dir,"iot_gallery");
        file = filelist.listFiles();
        InputStream is = null;
        try {
            Toast.makeText(this,"눌리냐"+count,Toast.LENGTH_SHORT).show();
            is = new FileInputStream(file[count]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap = BitmapFactory.decodeStream(is);

    }

    @Override
    public void run() {
        while(true){
            try {
                thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0);
        }
    }

}
