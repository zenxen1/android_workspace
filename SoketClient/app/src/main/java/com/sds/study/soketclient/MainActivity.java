package com.sds.study.soketclient;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    ViewPager viewPager;
    MyPagerAdapter myPagerAdapter;
    Toolbar toolbar;
    MyOpenHelper myOpenHelper;
    ChatDAO chatDAO;
    String TAG;
    Chat chat;
    Socket socket; //javaSE api 지원한다
    String ip;
    int port;
    Thread connectThread;//접속용
    ClientThread clientThread;//대화용
    Handler handler;
    MainActivity mainActivity;
    ChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG= this.getClass().getName();
        mainActivity=this;

        setContentView(R.layout.activity_main);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        toolbar = (Toolbar)findViewById(R.id.my_toolbar);

        viewPager.addOnPageChangeListener(this);
        /*앱바로 적용되는 시점*/
        setSupportActionBar(toolbar);
        init();
        //connectServer();

        chatFragment = (ChatFragment) myPagerAdapter.getItem(0);

        /*핸들러 재정의*/
        handler = new Handler(){
            public void handleMessage(Message message) {
                Bundle bundle =message.getData();
                String msg = bundle.getString("msg");
                chatFragment.txt_recive.append(msg+"\n");
            }
        };

    }
    /*데이터베이스 초기화 및 SQLiteDATAbase 객체얻기*/
    public void init(){
        myOpenHelper = new MyOpenHelper(this);
        /*아래의 메서드가 호출될때, onCreate,onUpgrade 호출된다*/
        SQLiteDatabase db=myOpenHelper.getWritableDatabase();
        chatDAO = new ChatDAO(db);
        chat = chatDAO.select(0);

        /*2번째 프레그먼트 접근하여, 알맞는 값 대입하기!!*/
        Log.d(TAG,"chat MainActivity"+chat.getPort());

        //2 ip와 포트를 얻어오자
        ip=chat.getIp();
        port=Integer.parseInt(chat.getPort());

    }
    /*회원 에니메이션 효과 주기*/
    public void setRotate(View view){
        Animation ani = AnimationUtils.loadAnimation(this,R.anim.ani_config);
        /*view.startAnimation(ani);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*메뉴를 선택하면*/
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_connect:
                /*3접속시도*/
                connectServer();
                break;
            case  R.id.menu_chat:
                viewPager.setCurrentItem(0);
                break;

            case R.id.menu_config:
                viewPager.setCurrentItem(1);
                setRotate(null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    /*페이지 넘어가는게 확정 되면 데이터를 넣는다.*/
    public void onPageSelected(int position) {
        if(position==1){
            ((ConfigFragment)myPagerAdapter.fragments[1]).setData(chat);
        }
    }
    public void onPageScrollStateChanged(int state) {
    }

    /*------------------------------------------------------
    1.SQLite에 등록된 정보대로, 소켓 서버에 접속하자!!
    --------------------------------------------------------*/
    public void connectServer(){
        /*소켓을 메모리에 올릴는 행위 = 접속시도!!*/
        connectThread = new Thread(){
            public void run() {
                try {
                    socket = new Socket(ip,port);

                    clientThread=new ClientThread(mainActivity,socket);
                    clientThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        connectThread.start();
    }
}
