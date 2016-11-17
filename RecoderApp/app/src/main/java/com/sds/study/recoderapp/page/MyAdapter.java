package com.sds.study.recoderapp.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by student on 2016-11-17.
 * viewPager는 껍데기에 불과하므로, 뷰페이저에 어떤 보여질 프래그먼트가 총 몇개고,
 * 어떤페이지를 보여줘야 할지를  관린해 주는 어댑터를 이용해야 한다...
 */

public class MyAdapter extends FragmentPagerAdapter{
    /*앱에서 사용할 프레그먼트를 준비해놓자*/

    Fragment[] fragments = new Fragment[3];

    public MyAdapter(FragmentManager fm) {
        super(fm);
        fragments[0] = new FragmentA();
        fragments[1] = new FragmentB();
        fragments[2] = new FragmentC();
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }
}
