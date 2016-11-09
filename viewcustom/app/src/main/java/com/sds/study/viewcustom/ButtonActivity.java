package com.sds.study.viewcustom;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by student on 2016-11-09.
 */

public class ButtonActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_layout);

       /* LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );*/

        Button button = new Button(this);
        button.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        button.setText("자바코드에 의한 버튼");

        MyButton bt = new MyButton(this);
        bt.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );


        LinearLayout layout = (LinearLayout) findViewById(R.id.insertlayout);

        layout.addView(button);
        layout.addView(bt);

    }
}
