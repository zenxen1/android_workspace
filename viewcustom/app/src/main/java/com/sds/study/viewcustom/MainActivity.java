package com.sds.study.viewcustom;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_main);*/

       /* linearlayout layout = new linearlayout(this);
        linearlayout.layoutparams layoutparams=null;
        layoutparams = new linearlayout.layoutparams(
                viewgroup.layoutparams.match_parent,
                viewgroup.layoutparams.match_parent
        );
        layout.setbackgroundcolor(color.yellow);
        layout.setlayoutparams(layoutparams);

        //버튼 생성하여 붙이자!!
        MyButton button = new MyButton(this);
        viewgroup.layoutparams buttonparams = null;
        buttonparams = new viewgroup.layoutparams(
                viewgroup.layoutparams.wrap_content,
                viewgroup.layoutparams.wrap_content
        );
        button.setlayoutparams(buttonparams);
        layout.addview(button);

        setcontentview(layout);*/

        setContentView(R.layout.activity_main);

    }
}
