package com.sds.study.fragmentapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 안드로이드의 화면이 태블릿의 등장으로 커짐에 따라 공간활용을 위해, 하나으 ㅣ스크린 내에서도 여러화면으로
 * 분활이 가능하도록 지원하기 위한 객체가 바로 프레그먼트인데 쉽게 생각하면 작은 액티비티라고
 * 생각하면 된다....
 * 주의)화면의 일부 이므로 반드시 액티비티에 의존해서 존재하게 된다...html과 비교하면 iframe과 비슷..
 */

public class FragmentA extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return   inflater.inflate(R.layout.a_layout,null);
    }
}
