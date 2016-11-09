package com.sds.study.activitystudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by student on 2016-11-09.
 */

public class DetailActivity extends Activity{
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        intent = getIntent();
        String data = intent.getStringExtra("data");
        System.out.println(data);

        TextView txt = (TextView)findViewById(R.id.a_txt);
        txt.setText(data);

    }

    public void btnClick(View view){
        this.finish();
    }
}
