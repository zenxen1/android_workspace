package com.sds.study.musicapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
리스트에 보여질 복합 위젯을 정의한다!!!
 하나의 위젯처럼 재사용이 가능하다...
 */

public class MusicItem extends LinearLayout implements View.OnClickListener{
    Music music;
    Boolean flag = true;
    ImageView img;
    public MusicItem(Context context, Music music) {
        super(context);
        this.music=music;
        init(context);
    }

    public MusicItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    //java 코드나 xml을 염두해 두고 준비시켜놓기 위해....
    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.music_item,this);

        TextView txt_title = (TextView) this.findViewById(R.id.txt_title);
        TextView txt_artist = (TextView)findViewById(R.id.txt_artist);
        TextView txt_file = (TextView)findViewById(R.id.txt_file);
        img = (ImageView) findViewById(R.id.img);
        txt_title.setText(music.getTitle());
        txt_artist.setText(music.getArtist());
        txt_file.setText(music.getFilename());
        img.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(flag) {
            img.setImageResource(R.drawable.stop);
            flag=false;
        }else{
            img.setImageResource(R.drawable.play);
            flag=true;
        }
    }

}
