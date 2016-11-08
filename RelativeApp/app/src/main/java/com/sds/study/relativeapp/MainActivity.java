package com.sds.study.relativeapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.writer);*/
        //순수한 자바 코드만으로도, 화면 디자인을 할수는 있다!!
        //때에 따라서는 자바코드로 작성해야 하는 경우도 있다!!
        layout = new LinearLayout(this);

        //파라미터 객체 생성
        LinearLayout.LayoutParams params=null;
        params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.YELLOW);

        layout.setLayoutParams(params);
        //버튼 생성하여 레이아웃 객체에 부착하자!!
        Button bt = new Button(this);

        ViewGroup.LayoutParams bt_params = null;
        bt_params=new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        bt.setLayoutParams(bt_params);
        bt.setText("나버튼");

        layout.addView(bt);

        //리스너와 연결
        bt.setOnClickListener(this);

        //화면에 보여주기
        setContentView(layout);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this,"나 눌렀어?", Toast.LENGTH_SHORT).show();

    }
}
