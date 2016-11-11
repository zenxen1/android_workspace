package com.sds.study.musicapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    String TAG;
    //음악, 영상등의 미디어를 재생하려면 MediaPlayer 객체를 사용
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TAG=this.getClass().getName();


    }
    public void btnClick(View view){

        if(view.getId()==(R.id.play)){
            Log.d(TAG,"재생합니다.");
            //음원을 결정하자
            //Uri란? 자원의 위치정보를 가진 객체로서, 특정자원을 접근하려면 일정한 정해진
            //형식을 알아야한다...ex)인터넷상의 자원URl : http://서버주소/디렉토리
            Uri uri = Uri.parse("android.resource://com.sds.study.musicapp/" + R.raw.a);
            try {
                if(mediaPlayer==null) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(this, uri);
                    mediaPlayer.prepare();
                }
                mediaPlayer.start();//재생
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(view.getId()==(R.id.pause)){
            Log.d(TAG,"잠시멈춤니다");
            if(mediaPlayer!=null) {
                mediaPlayer.pause();
            }
        }else if(view.getId()==(R.id.stop)){
            Log.d(TAG,"재생종료");
            if(mediaPlayer!=null) {
                mediaPlayer.stop();
                mediaPlayer = null;
            }
        }
    }
}
