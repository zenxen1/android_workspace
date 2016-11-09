package com.sds.study.viewcustom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

/**
 * Created by student on 2016-11-09.
 */
//이클래스의 사용을 xml에서 할지, 순수자바코드에서 할지를 알수 없기 때문에 , 생성자 오버로딩으로 2개를 정의해 놓았따

public class MyButton extends Button{
    String TAG;
    public MyButton(Context context) {
        super(context);
        this.setText("내가 정의한 버튼");
        TAG = this.getClass().getName();
        Log.d(TAG,"인수한개짜리 생성자 호출!!");
    }
//XML 을 이용하여 위젯을 메모리에 올릴때는 반드시 attr 인수로 갖는 생성자를 정의해야 한다.
    public MyButton(Context context, AttributeSet attrs) {
        super(context,attrs);//부모인 버튼의 생성자 호출
        this.setText("내가 정의한 버튼");
        Log.d("MyButton","인수두개짜리 생성자 호출!!");
    }
}
