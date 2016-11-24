package com.sds.study.soketclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *Fragment란 액티비티 내의 화면의 일부를 관리하는 객체
 * 따라서 액티비티의 생명주기에 의존적이며, 화면을 관리한다는 점에 있어서는 액티비티와
 * 상당히 유사하므로, 자체적으로 생명주기 메서드가 있다.
 */

public class ChatFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatfragment_layout,null);
        return view;
    }
}
