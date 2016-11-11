package com.sds.study.herolistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String[] arr_name = {"배트맨","캡틴","플래쉬","그린","아이언","퍼니셔","로빈","스파이더","슈퍼","우먼"};
    String[] arr_phone={
            "010-2030-2391",
            "010-2030-2392",
            "010-2030-2393",
            "010-2030-2394",
            "010-2030-2395",
            "010-2030-2396",
            "010-2030-2397",
            "010-2030-2398",
            "010-2030-2399",
            "010-2030-2390"
    };
    int[] thumb = {
            R.drawable.batman,
            R.drawable.captain,
            R.drawable.flashman,
            R.drawable.green,
            R.drawable.ironman,
            R.drawable.punisher,
            R.drawable.robin,
            R.drawable.spiderman,
            R.drawable.superman,
            R.drawable.wonder
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hero_list);

        //layout을 얻어와서 동적으로 아이템들을 채워넣자
        LinearLayout layout = (LinearLayout)findViewById(R.id.layout);

        //item.xml을 인플레이션 시켜서 자식으로 등록
       // LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);

        for(int i=0;i<10;i++) {
            //데이터생성
            Hero hero = new Hero();
            hero.setName(arr_name[i]);
            hero.setPhone(arr_phone[i]);
            hero.setImg(thumb[i]);

            //복합위젯생성
            HeroItem heroItem = new HeroItem(this,hero);
            layout.addView(heroItem);

            heroItem.setOnClickListener(this);

           /* LinearLayout itemLayout = (LinearLayout)inflater.inflate(R.layout.hero_item,null);
            layout.addView(itemLayout);

            TextView txt_name = (TextView) itemLayout.findViewById(R.id.tx_name);
            TextView txt_phone = (TextView)itemLayout.findViewById(R.id.tx_phone);
            ImageView img = (ImageView)itemLayout.findViewById(R.id.img);

            txt_name.setText(arr_name[i]);
            txt_phone.setText(arr_phone[i]);
            img.setImageResource(thumb[i]);


            itemLayout.setOnClickListener(this);*/
        }
    }

    @Override
    public void onClick(View view) {
        TextView txt_name = (TextView) view.findViewById(R.id.tx_name);

        //액티비티는 호출만 가능할뿐, 우리가 메모리에 직접 생성할수 없다 이떄 개발자는 시스템에 원하는 내용
        //을 즉 의도를 보여야한다. 즉시스템이 이해할수 있는 형태로 표현해야하는데 이떄 사용하는객체가 intent

        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra("data",txt_name.getText());
        this.startActivity(intent);

        Toast.makeText(this,"히어로이름"+txt_name.getText(),Toast.LENGTH_SHORT).show();
    }
}
