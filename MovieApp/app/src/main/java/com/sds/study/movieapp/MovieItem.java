package com.sds.study.movieapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by student on 2016-11-14.
 */

public class MovieItem extends LinearLayout {
    Context context;

    public MovieItem(Context context) {
        super(context);
        this.context=context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.movie_item,this);



    }
    public void setData(Movie movie){
        TextView txt_title = (TextView) this.findViewById(R.id.txt_title);
        TextView txt_regdate = (TextView) this.findViewById(R.id.txt_regdate);
        ImageView img = (ImageView)this.findViewById(R.id.img);

        txt_title.setText(movie.getTitle());
        txt_regdate.setText(movie.getRegdate());

        //이미지 대입 고민해보기...
        try {
            InputStream is = context.getAssets().open(movie.getImg());
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            img.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
