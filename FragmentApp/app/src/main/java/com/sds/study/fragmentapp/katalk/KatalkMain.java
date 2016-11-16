package com.sds.study.fragmentapp.katalk;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.sds.study.fragmentapp.R;

/**
 * Created by student on 2016-11-16.
 */

public class KatalkMain extends FragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.katalkmain_layout);

        Fragment red = new RedFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container,red);
        transaction.commit();
    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_a:;
            case R.id.bt_b:;
            case R.id.bt_c:;
        }
    }
}
