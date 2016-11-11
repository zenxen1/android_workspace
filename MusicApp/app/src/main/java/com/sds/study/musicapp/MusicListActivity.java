package com.sds.study.musicapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by student on 2016-11-11.
 */

public class MusicListActivity extends Activity{
    String TAG;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=this.getClass().getName();
        setContentView(R.layout.music_list);
        //리스트뷰와 어탭터와 연결!!!
        ListView listView = (ListView) findViewById(R.id.listView);
        MusicAdapter adapter = new MusicAdapter(this);
        listView.setAdapter(adapter);





        //안드로이드앱의 일반 자원을 두는 곳중 raw, asset 이라는 디렉토리가 있는데,
        //raw는 R.java에 등록되므로 상수 취급 되고,asset는 그냥 일반 디렉토리로 간주  하면 된다
       /* AssetManager manager = this.getAssets();
        try {
            String[] dir = manager.list("/");
            Log.d(TAG,"dir은"+dir.length);
            for(int i=0;i<dir.length;i++){
                Log.d(TAG,dir[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    public void btnClick(View view){
        Intent intent = new Intent(this,DetailActivity.class);
        TextView txt_artist = (TextView)view.findViewById(R.id.txt_artist);
        TextView txt_title = (TextView)view.findViewById(R.id.txt_title);
        TextView txt_file = (TextView)view.findViewById(R.id.txt_file);
        Toast.makeText(this,"눌렀냐?"+txt_artist.getText()+","+txt_title.getText(),Toast.LENGTH_SHORT).show();
        intent.putExtra("title",txt_title.getText());
        intent.putExtra("artist",txt_artist.getText());
        intent.putExtra("file",txt_file.getText());


        this.startActivity(intent);
    }


}