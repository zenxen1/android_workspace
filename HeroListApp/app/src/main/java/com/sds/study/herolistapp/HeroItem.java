package com.sds.study.herolistapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 추후 재사용성을 고려하여, 여러 위젯이 조합된 형태의 즉 새로운 형태의 복합형 위젯을 정의해 보자!!
 * 나만의 복합형 위젯을 정의해보자!!!
 */

public class HeroItem extends LinearLayout{

    public HeroItem(Context context, Hero hero) {
        super(context);
        //xml을 여기서 인플레이션 시키자!!
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.hero_item,this);

        TextView txt_name = (TextView) this.findViewById(R.id.tx_name);
        TextView txt_phone = (TextView) this.findViewById(R.id.tx_phone);
        ImageView img = (ImageView)this.findViewById(R.id.img);

        txt_name.setText(hero.getName());
        txt_phone.setText(hero.getPhone());
        img.setImageResource(hero.getImg());
    }

    public HeroItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
