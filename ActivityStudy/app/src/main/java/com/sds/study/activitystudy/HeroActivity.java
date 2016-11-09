package com.sds.study.activitystudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by student on 2016-11-09.
 */

public class HeroActivity extends Activity{
    int[] photo = {
            R.drawable.batman,
            R.drawable.batman_hero,
            R.drawable.captain_america,
            R.drawable.captainamerica,
            R.drawable.deadpool,
            R.drawable.flash,
            R.drawable.flash_hero,
            R.drawable.ironman,
            R.drawable.punisher,
            R.drawable.superman
    };

    String[] namearr = {"배오맨","슈퍼맨","비트맨","어밴맨","플래맨","아이언맨","학봉맨","퍼쉬맨","데드맨","울구맨"};
    String[] telarr = {"010-2323-2342","010-2323-2342","010-2323-2342","010-2323-2342","010-2323-2342","010-2323-2342","010-2323-2342","010-2323-2342","010-2323-2342","010-2323-2342"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_layout);

        LinearLayout linear = (LinearLayout) findViewById(R.id.liner);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        for(int i=0;i<10;i++) {
            RelativeLayout item = (RelativeLayout) inflater.inflate(R.layout.hero_layout, null);

            TextView name = (TextView) item.findViewById(R.id.b_name);
            TextView tel = (TextView) item.findViewById(R.id.b_tel);
            ImageView img = (ImageView) item.findViewById(R.id.a_img);

            name.setText(namearr[i]);
            tel.setText(telarr[i]);
            img.setImageResource(photo[i]);

            linear.addView(item);
        }
    }

    public void btnClick(View view){
        Toast.makeText(this,"눌렀냐",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,DetailActivity.class);

        TextView bname=(TextView)view.findViewById(R.id.b_name);
        System.out.print(bname.getText());
        intent.putExtra("data",bname.getText());

        this.startActivity(intent);
    }
}
