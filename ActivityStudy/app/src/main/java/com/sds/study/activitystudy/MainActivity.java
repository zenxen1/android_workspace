package com.sds.study.activitystudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick(View view){
        Toast.makeText(this,"나눌렀어",Toast.LENGTH_SHORT).show();

        //빨간 액티비티 활성화, 즉 호출하자!!!
        //안드로이드에서 앱제작시 사용빈도가 높은 기능을 가리켜 안드로이드의 주요 컴포넌트라고 하는데,
        // 이중 하나가 액티비티이며, 이 주요 컴포넌트들은 모두 개발자가 제어할수 없다. 즉 생성할수 없다!!
        //이유 - 시스템이 관리하기 때문에...(쓰레드...서블릿...)
        //시스템에 부탁하자~~~~
        //개발자가 안드로이드 시스템에 원하는 요구 사항에 대한 의도(Intent)를 보여야 한다.
        Intent intent = new Intent(this,RedActivity.class);
        intent.putExtra("data","tiger");
        this.startActivity(intent);


    }


}
