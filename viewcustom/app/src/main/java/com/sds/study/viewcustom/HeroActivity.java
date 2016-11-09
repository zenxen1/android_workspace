package com.sds.study.viewcustom;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 히이로 한세트 (복합 위젯)를 xml로 정의해놓고, 해당 xml을 읽어들여 반복문 돌려보자!!!
 */

public class HeroActivity extends Activity{

    int[] photo = {
            R.drawable.avangers,
            R.drawable.batman,
            R.drawable.batman_hero,
            R.drawable.captain_america,
            R.drawable.captainamerica,
            R.drawable.flash,
            R.drawable.flash_hero,
            R.drawable.ironman,
            R.drawable.punisher,
            R.drawable.superman
    };

    String[] title ={
            "어벤져스","배트맨","배트맨히어로","캡틴아메리카","플래쉬","플래쉬히어로","아이언맨",
            "퍼니쉬","수퍼맨","수퍼맨"
    };

    String[] p = {
            "1000","1435","1123","1200","1234","45554","10121","234","34643","342"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setcontentview는 하나의 화면 전체를 차지하게 되므로, 히어로 하나만 생성하는 목적으로는 적당하지 않음
        //개발자가 특정 xml을 로드하는 코드를 작성해야 한다.
        //xml에 명시한 태그를 실제 안드로이드 객체화 시켜 메모리에 올리는 과정을 inflation 이라한다
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        //setContentview에는 내부적으로 inflation이 일어남
        setContentView(R.layout.gallery_layout);
        GridLayout grid = (GridLayout) findViewById(R.id.grid);

        //그리드 레이아웃 안에 원하는 갯수만큼 반복문 돌려서 추가한다!!
        for(int i=0;i<10;i++) {
            RelativeLayout item = (RelativeLayout) inflater.inflate(R.layout.hero_layout, null);
            ImageView img = (ImageView) item.findViewById(R.id.img);
            img.setImageResource(photo[i]);

            TextView name = (TextView) item.findViewById(R.id.name);
            TextView power = (TextView) item.findViewById(R.id.power);
            name.setText(title[i]);
            power.setText(p[i]);
            grid.addView(item);
        }
    }
}
