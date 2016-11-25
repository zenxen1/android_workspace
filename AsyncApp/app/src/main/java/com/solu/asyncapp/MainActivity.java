/*
    안드로이드에서 비동기방식의 업무를수행하려면
    개발자가  스레드를 구현하고, 만일 UI까지 제어하려면 Handler 의
    도움이 필요하다.. 이러한 업무를 보다 편하게 처리할 수 있도록
    지원하는 새로운 객체를 사용해보자!!
    AsyncTask : 웹전용은 아니다...하지만 웹프로그래밍시 비동기가
    압도적으로 대세인 요즘 이 객체의 사용빈도가 높을수밖에 없다...
*/
package com.solu.asyncapp;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    MyAsync myAsync;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /*----------------------------------------------
    웹서버에 데이터 등록 요청!! ( NFC 카드 가까이 댈때 할 예정임...)
    ----------------------------------------------*/
    public void regist(){
        WriteAsync writeAsync = new WriteAsync();
        writeAsync.execute();
    }

    /*----------------------------------------------
    웹서버에 데이터 목록 요청!!
    ----------------------------------------------*/
    public void requestList(){
        myAsync = new MyAsync();
        myAsync.execute("http://192.168.0.35:9090/member/list.jsp");
    }

    public void btnClick(View view){
        if(view.getId() == R.id.bt_regist){
            regist();
        }else if(view.getId() == R.id.bt_list){
            requestList();
        }
    }


}








