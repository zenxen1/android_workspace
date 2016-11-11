package com.sds.study.herolistapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by student on 2016-11-10.
 */

public class DetailActivity extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        TextView txt_msg = (TextView) findViewById(R.id.txt_msg);

        Intent intent = this.getIntent();
        txt_msg.setText(intent.getStringExtra("data"));

    }
    public void btnClick(View view){
        this.finish();
    }
}
