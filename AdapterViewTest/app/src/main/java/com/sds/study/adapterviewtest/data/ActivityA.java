package com.sds.study.adapterviewtest.data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sds.study.adapterviewtest.R;

/**
 * Created by student on 2016-11-10.
 */

public class ActivityA extends Activity{
    EditText receive_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_layout);
    }

    public void btnClick(View view){
        Intent intent = new Intent(this,ActivityB.class);
        EditText send_msg = (EditText) findViewById(R.id.send_msg);
        receive_msg = (EditText) findViewById(R.id.receive_msg);
        intent.putExtra("data",send_msg.getText().toString());

        //this.startActivity(intent);
        //다른 액티비티 호출시 그결과를 다시 받아올 경우엔
        startActivityForResult(intent,2002);
    }

    //호출한 액티비티가 응답할경우 아래의 메서드가 호출!!!
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==2002 && requestCode==Activity.RESULT_OK){
           String result = data.getStringExtra("data");

            receive_msg.setText(result);

        }
    }
}
