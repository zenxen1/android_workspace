package com.sds.study.recoderapp.record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sds.study.recoderapp.R;

import java.util.List;

/**
 * Created by student on 2016-11-17.
 */

public class DetailFragment extends Fragment implements View.OnClickListener{
    ListFragment listFragment;
    String filename;
    TextView txt_filename;
    ImageView disk;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,null);
        txt_filename = (TextView)view.findViewById(R.id.txt_filename);
        /*페이지를 구성하는 나 아닌 다른 페이지 프레그먼트를 접근해 보자*/
        FragmentManager manager=this.getFragmentManager();
        List <Fragment> list = manager.getFragments();
        listFragment = (ListFragment) list.get(0);

        Button bt_play = (Button)view.findViewById(R.id.bt_play);
        disk = (ImageView)view.findViewById(R.id.disk);
        bt_play.setOnClickListener(this);


        return view;
    }

    /*화면에 보여지기 시작할때, 리스트 Fragment의 선택된 파일명을 접근해보자!!*/

 /*  시점이 빠르다아앙 @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(),"리쥼의 파일명 filename:"+listFragment,Toast.LENGTH_SHORT).show();
    }*/
    public void play(){
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.disk);
        /*안드로리에서 에니매이션의 대상이 되는 주체는 모든 뷰이다!!!*/
        disk.startAnimation(animation);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.bt_play){
            play();
        }
    }
}
