package com.sds.study.recoderapp.page;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sds.study.recoderapp.R;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    MyAdapter myAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        myAdapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myAdapter);
    }
    public void btnClick(View view){
        switch (view.getId()){
            case R.id.bt_A :viewPager.setCurrentItem(0);break;
            case R.id.bt_B :viewPager.setCurrentItem(1);break;
            case R.id.bt_C :viewPager.setCurrentItem(2);break;
        }
    }
}
