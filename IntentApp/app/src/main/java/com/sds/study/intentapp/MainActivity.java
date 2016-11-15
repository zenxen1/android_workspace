package com.sds.study.intentapp;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_CALL_PERMISSION=1;
    static final int REQUEST_READ_PERMISSION=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    //전화앱의 다이얼 패드 화면 호출하기.....
    public void bt1(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:01026743110"));
        startActivity(intent);
    }
    //검색어를 지정하여 검색창 띄우기!!
    public void bt2(View view){
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY,"하야");
        startActivity(intent);
    }

    public void bt3(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setData(Uri.parse("geo:0,0?q=역삼동"));
        intent.setData(Uri.parse("geo:37,127?z=20"));
        startActivity(intent);
    }

    public void bt4(View view){
        int callPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if(callPermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.CALL_PHONE
            },REQUEST_CALL_PERMISSION);
        }else{
            callphone();
        }
    }

    //유저가 선택한 상황을 판단하는 메서드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       switch (requestCode){
           case REQUEST_CALL_PERMISSION:
               if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   callphone();
               }
           case REQUEST_READ_PERMISSION:
               if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   playAudio();
               }
       }
    }

    public void callphone(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:01026743110"));
        startActivity(intent);

    }

    public void bt5(View view){
        int readPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if(readPermission==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{
               Manifest.permission.READ_EXTERNAL_STORAGE
            },REQUEST_READ_PERMISSION);
        }else{
            playAudio();
        }
    }
    public void playAudio(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File dir = Environment.getExternalStorageDirectory();
        File file = new File(dir,"iot_music/a.mp3");

        intent.setDataAndType(Uri.fromFile(file),"audio/*");
        startActivity(intent);

    }

    public void bt6(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.naver.com"));
        startActivity(intent);
    }

    public void bt7(View view){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:zenxen@naver.com"));
        startActivity(intent);
    }

    public void bt8(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("smsto:01026743110"));
        intent.putExtra("sms_body","잘지내?");
        startActivity(intent);
    }
}
