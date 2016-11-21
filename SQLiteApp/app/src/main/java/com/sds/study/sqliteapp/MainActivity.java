package com.sds.study.sqliteapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    MyHelper myHelper; /*데이터베이스구축*/
    public static SQLiteDatabase db;/*데이터베이스쿼리문 제어*/
    EditText txt_id;
    EditText txt_password;
    String TAG;
    ListView listView;
    MyListAdapter myListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getName();
        setContentView(R.layout.activity_main);
        init();
        txt_id=(EditText)findViewById(R.id.txt_id);
        txt_password=(EditText)findViewById(R.id.txt_password);
        listView=(ListView)findViewById(R.id.listView);
        myListAdapter = new MyListAdapter(this);
        listView.setAdapter(myListAdapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(myListAdapter!=null){
            myListAdapter.getList();
            myListAdapter.notifyDataSetChanged();
        }
    }

    /*데이터베이스 초기화*/
    public void init(){
        myHelper = new MyHelper(this,"iot.sqlite",null,1);
        db =myHelper.getWritableDatabase();
    }
    public void regist(){
        /*이앱이 보유한 sqllite 데이터베이스에 insert*/
        String sql ="insert into member(id,password)";
        sql += " values(?,?)";
        String id = txt_id.getText().toString();
        String password = txt_password.getText().toString();
        db.execSQL(sql,new String[]{id,password});
        txt_id.setText("");
        txt_password.setText("");
        Log.d(TAG,"등록완료");

        /*listview 갱신*/
        myListAdapter.getList();
        myListAdapter.notifyDataSetChanged();
    }

    public void btnClick(View view){
        regist();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView item_member_id = (TextView)view.findViewById(R.id.txt_member_id);
        TextView item_id = (TextView)view.findViewById(R.id.txt_id);
        TextView item_password = (TextView)view.findViewById(R.id.txt_password);

        Member member = new Member();
        member.setMember_id(Integer.parseInt(item_member_id.getText().toString()));
        member.setId(item_id.getText().toString());
        member.setPassword(item_password.getText().toString());


        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra("member",member);
        startActivity(intent);
    }
}
