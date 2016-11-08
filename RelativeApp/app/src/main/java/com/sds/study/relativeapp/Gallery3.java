package com.sds.study.relativeapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by student on 2016-11-08.
 */

public class Gallery3 extends Activity implements View.OnClickListener {
    LinearLayout img;
    Button bt_load;
    int photo[] = {
            R.drawable.img0,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery3);
        img = (LinearLayout) findViewById(R.id.img);
        bt_load = (Button) this.findViewById(R.id.bt_load);

        bt_load.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        for (int index = 0; index < photo.length - 1; index++) {
            ImageView gview = new ImageView(this);
            ViewGroup.LayoutParams gviewparam = null;
            /*gviewparam = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            gview.setLayoutParams(gviewparam);*/
            gview.setImageResource(photo[index]);
            gview.setPadding(0,100,0,100);

            img.addView(gview);

        }
    }

}
