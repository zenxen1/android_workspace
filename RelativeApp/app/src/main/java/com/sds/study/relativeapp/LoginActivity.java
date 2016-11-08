package com.sds.study.relativeapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by student on 2016-11-08.
 */

public class LoginActivity extends Activity{
    LinearLayout layout;
    LinearLayout layout1;

    //앱이 가동되면, 이클래스가 무조건 실행됨....
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = new LinearLayout(this);
        LinearLayout.LayoutParams params = null;
        params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        layout.setPadding(40,40,40,40);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.YELLOW);
        layout.setLayoutParams(params);

        layout1 = new LinearLayout(this);
        LinearLayout.LayoutParams params1 = null;
        params1 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.setBackgroundColor(Color.GRAY);

        layout1.setLayoutParams(params1);

        TextView textView = new TextView(this);

        ViewGroup.LayoutParams tv_params = null;
        tv_params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(tv_params);
        textView.setText("로그인");
        textView.setTextSize(100);
        textView.setTextColor(Color.BLUE);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        EditText editText = new EditText(this);
        LinearLayout.LayoutParams et_params = null;
        et_params = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                3
        );
        editText.setLayoutParams(et_params);
        editText.setText("입력");
        editText.setBackgroundColor(Color.BLUE);


        Button button = new Button(this);
        LinearLayout.LayoutParams bt_params = null;
        bt_params = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1
        );
        button.setLayoutParams(bt_params);
        button.setText("누르시오");


        layout.addView(textView);
        layout1.addView(editText);
        layout1.addView(button);
        layout.addView(layout1);

        setContentView(layout);
    }
}
