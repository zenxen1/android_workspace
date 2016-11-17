package com.sds.study.recoderapp.record;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by student on 2016-11-17.
 */

public class RecordPagerAdapter extends FragmentPagerAdapter{

    Fragment[] fragments = new Fragment[2];



    public RecordPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments[0] = new ListFragment();
        fragments[1] = new DetailFragment();
    }

    public Fragment getItem(int position) {
        return fragments[position];
    }

    public int getCount() {
        return fragments.length;
    }
}
