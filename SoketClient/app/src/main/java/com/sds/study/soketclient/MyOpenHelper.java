package com.sds.study.soketclient;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * SQLite 데이터베이스 파일(data/data/나의패키지/databases)
 * 은 이미 위치가 정해져있고, 일반유저는 절대로 접근할수 없는 내부
 * storage 이기 때문에 프로그래밍적으로 데이터베이스 파일 생성 및 DDL을 수행해야 하는데,
 * 이 작업은 개발자에게 번거로운 작업이므로 SQLiteOpenHelper라는 클래스가 지원된다.
 */

public class MyOpenHelper extends SQLiteOpenHelper{
    String TAG;
    ChatDAO chatDAO;
    public MyOpenHelper(Context context) {
        super(context, "chat.splite", null, 2);
        TAG=this.getClass().getName();
    }

    /*data/data/나의패키지/databases*/
    /*지정한 이름과 동일한 파일이 없다면 아래의 파일을 호출*/
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append("create table chat(");
        sb.append("chat_id integer primary key autoincrement");
        sb.append(",ip varchar(15)");
        sb.append(",port varchar(10)");
        sb.append(",nickname varchar(20)");
        sb.append(",img varchar(30)");
        sb.append(");");
        try {
            db.execSQL(sb.toString());
            Log.d(TAG,"DBL 생성성공");
            chatDAO = new ChatDAO(db);
            Chat chat = new Chat();
            chat.setIp("192.168.0.12");
            chat.setPort("9999");
            chat.setNickname("brain");
            chat.setImg("punisher.png");


            chatDAO.insert(chat);
        } catch (SQLException e) {
            Log.d(TAG,"DBL 생성실패");
            e.printStackTrace();
        }
    }

    /*이미 같은 파일명이 존재하되, version 숫자가 틀려야 한다*/
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
