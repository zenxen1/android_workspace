package com.sds.study.movieapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * 동영상이 재생되도록 한다... 안드로이드에서 영상 재생은 videoView로 처리한다...
 */

public class DetailActivity extends Activity{
    VideoView videoView;
    MediaController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_layout);
        videoView = (VideoView)findViewById(R.id.videoView);
        controller = new MediaController(this);

        Intent intent=getIntent();
       /* String filename = intent.getStringExtra("filename");*/

        Movie movie = intent.getParcelableExtra("dto");
        TextView txt_filename = (TextView)findViewById(R.id.filename);
        txt_filename.setText(movie.getTitle());

        videoView.setMediaController(controller);
        //videoView.setVideoPath(filename);//재생할 경로 등록
        //videoView.start();
    }
    public void stop(View view){
        videoView.seekTo(0);
        videoView.pause();
    }
    public void pause(View view){
        videoView.pause();
    }
    public void play(View view){
        videoView.start();
    }


}
