package com.sds.study.relativeapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by student on 2016-11-08.
 */
/*activity란 화면 관리자 이다*/
public class Gallery extends Activity implements View.OnClickListener{
    //서브릿의 init이 클래스의 초기화를 담당한다면, 안드로이드에서의 클래스 초기화는 onCreate 메서드가 담당
    ImageView img;
    Button bt_prev;
    Button bt_next;
    //안드로이드는 res 디렉토리에 넣어지는 모든 자원을 내부적으로 상수화 시킨다..따라서 int로 제어가 가능
    //res/layout/xml파일 , res/drawable/img0 파일...
    int[] photo = {
            R.drawable.img0,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6
    };
    int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //XML에 명시된 객체가 메모리로 로드됨...(inflation)
        setContentView(R.layout.gallery2);

        img=(ImageView)findViewById(R.id.view);

        //버튼과 리스너와 연결!!
        bt_prev = (Button) this.findViewById(R.id.bt_prev);
        bt_next = (Button) this.findViewById(R.id.bt_next);

        bt_next.setOnClickListener(this);
        bt_prev.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //이벤트를 일으킨 뷰가 인수로 넘어옴...
        //스윙의 actionEvent가 넘어오는것과 비슷함
        if(view.equals(bt_prev)){
            if(index>0) {
                index--;
            }else {
                Toast.makeText(this, "이전사진없다아", Toast.LENGTH_SHORT).show();
            }
        }else if(view.equals(bt_next)){
            //img.setImageResource(R.drawable.img1);
            if(index<photo.length-1) {
                index++;
            }else {
                Toast.makeText(this, "다음사진있다아", Toast.LENGTH_SHORT).show();
            }
        }
        img.setImageResource(photo[index]);

    }
}
