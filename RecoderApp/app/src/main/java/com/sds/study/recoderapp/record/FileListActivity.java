package com.sds.study.recoderapp.record;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sds.study.recoderapp.R;

/**
 * Created by student on 2016-11-17.
 */

public class FileListActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    ViewPager viewPager;
    RecordPagerAdapter pagerAdapter;
    String TAG;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TAG = this.getClass().getName();
        Log.d(TAG,"FilelistActivity=?"+this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        Intent intent = getIntent();

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        pagerAdapter = new RecordPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      /*  Log.d(TAG,"onPageScrolled");*/
    }

    @Override
    /*페이지 선택이 확정되면....commit되면....*/
    public void onPageSelected(int position) {
        Log.d(TAG,"onPageSelected");
        DetailFragment detailFragment = (DetailFragment)pagerAdapter.fragments[1];
        ListFragment listFragment = (ListFragment)pagerAdapter.fragments[0];
        detailFragment.txt_filename.setText(listFragment.filename);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG,"onPageScrollStateChanged");
    }
}
