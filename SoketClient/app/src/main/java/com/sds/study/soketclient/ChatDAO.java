package com.sds.study.soketclient;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

/**
 * Created by lee on 2016-11-23.
 */

public class ChatDAO  {
    SQLiteDatabase db;
    String TAG;

    public ChatDAO(SQLiteDatabase db) {
        this.db = db;
        TAG=this.getClass().getName();
    }

    public void insert(Chat chat){
        String sql = "insert into chat(ip,port,nickname,img)";
                sql += " values(?,?,?,?)";
        try {
            db.execSQL(sql,new String[]{
                    chat.getIp(),chat.getPort(),chat.getNickname(),chat.getImg()
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Chat select(int ctat_id){
        Cursor rs =db.rawQuery("select * from chat",null);
        Chat chat = null;
        if(rs.moveToNext()){
            chat = new Chat();
            Log.d(TAG,"dao의 chat은"+chat);
            chat.setIp(rs.getString(rs.getColumnIndex("ip")));
            chat.setNickname(rs.getString(rs.getColumnIndex("nickname")));
            chat.setPort(rs.getString(rs.getColumnIndex("port")));
            chat.setImg(rs.getString(rs.getColumnIndex("img")));
            Log.d(TAG,"dao의 chat은"+chat.getNickname());
        }
        return chat;
    }
    public void update(Chat chat){
        String sql ="update chat set";
        sql += " ip=?,port=?,nickname=?";

        try {
            db.execSQL(sql,new String[]{
                    chat.getIp(),
                    chat.getPort(),
                    chat.getNickname()
            });
            Log.d(TAG,"Update성공");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int chat_id){
    }
}
