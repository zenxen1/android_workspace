package com.sds.study.sqliteapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by student on 2016-11-18.
 */

public class DetailActivity extends AppCompatActivity{
    ImageView img;
    LinearLayout layout;

    TextView txt_member_id;
    EditText txt_id;
    EditText txt_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txt_member_id = (TextView) findViewById(R.id.txt_member_id);
        txt_id = (EditText)findViewById(R.id.txt_id);
        txt_password = (EditText)findViewById(R.id.txt_password);

        Intent intent = getIntent();
        Member member = intent.getParcelableExtra("member");

        txt_member_id.setText(Integer.toString(member.getMember_id()));
        txt_id.setText(member.getId());
        txt_password.setText(member.getPassword());


        img=(ImageView)findViewById(R.id.img);
        layout=(LinearLayout)findViewById(R.id.layout);
        AnimationDrawable drawable = (AnimationDrawable) img.getDrawable();
        drawable.start();
        AnimationDrawable back =(AnimationDrawable) layout.getBackground();
        back.start();
    }
    public void list(View view){
        finish();
    }
    public void modify(View view){
        String sql = "update member set id=?, password=? where member_id=?";
        MainActivity.db.execSQL(sql,new String[]{
                txt_id.getText().toString(),
                txt_password.getText().toString(),
                txt_member_id.getText().toString()
        });
        finish();
    }
    public void delete(View view){
        String sql = "delete from member where member_id="+txt_member_id.getText().toString();
        MainActivity.db.execSQL(sql);
        finish();
    }
}
