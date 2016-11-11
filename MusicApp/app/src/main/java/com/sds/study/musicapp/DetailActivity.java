package com.sds.study.musicapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by student on 2016-11-11.
 */

public class DetailActivity extends Activity{
    MediaPlayer mediaPlayer;
    AssetFileDescriptor fd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String artist = intent.getStringExtra("artist");
        String file = intent.getStringExtra("file");
        System.out.print(file);

        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        TextView detail_artist = (TextView) findViewById(R.id.detail_artist);
        detail_title.setText(title);
        detail_artist.setText(artist);

        AssetManager assetManager = this.getAssets();
        try {
            fd = assetManager.openFd(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnClick(View view){

        if(view.getId()==(R.id.bt_return)) {
            finish();
        }else if(view.getId()==(R.id.bt_play)){
            try {
                if(mediaPlayer==null) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(fd.getFileDescriptor(),fd.getStartOffset(),fd.getLength());
                    mediaPlayer.prepare();
                }
                mediaPlayer.start();//재생
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(view.getId()==(R.id.bt_stop)){
            if(mediaPlayer!=null) {
                mediaPlayer.stop();
                mediaPlayer = null;
            }
        }

    }
}
