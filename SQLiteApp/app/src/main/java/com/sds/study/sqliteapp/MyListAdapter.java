package com.sds.study.sqliteapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * 리스트뷰에 보열질 화면이 단일 위젯이 아닌,2개이상으로 구성된 복합 위젯을 경우 개발자가
 * 디자인을 정의하므로, 어탭터를 재정의한다.
 */

public class MyListAdapter extends BaseAdapter{
    Context context;
    SQLiteDatabase db;
    String TAG;
    ArrayList <Member> memberList = new ArrayList<Member>();

    public MyListAdapter(Context context) {
        this.context=context;
        MainActivity mainActivity = (MainActivity)context;
        db = mainActivity.db;
        TAG = this.getClass().getName();
        getList();
    }

    /*데이터베이스로부터 레코드 가져오기*/
    public void getList(){
        String sql = "select * from member";
        Cursor rs = db.rawQuery(sql,null);
        /*기존 arraylist 모두 삭제*/
        memberList.removeAll(memberList);

        while(rs.moveToNext()){
            int member_id = rs.getInt(rs.getColumnIndex("member_id"));
            String id = rs.getString(rs.getColumnIndex("id"));
            String password = rs.getString(rs.getColumnIndex("password"));
            Log.d(TAG,"id"+id+"password"+password);

            Member dto = new Member();
            dto.setMember_id(member_id);
            dto.setId(id);
            dto.setPassword(password);

            memberList.add(dto);
        }
        /*ListView로 하여금, 다시 어댑터의 정보를 가져가게 하자~~갱신시키자*/


    }

    @Override
    public int getCount() {
        return memberList.size();
    }

    @Override
    public Object getItem(int i) {
        return memberList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return memberList.get(i).getMember_id();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        /*해당 index에 아이템이 이미 채워져있다면....*/
        View view=null;
        Member member = memberList.get(i);
        if(convertView!=null){
            view = convertView;
            MemberItem item = (MemberItem)view;
            item.setMember(member);
        }else{
        /*해당 index에 아무것도 없는 상태라면...*/
            view = new MemberItem(context,member);
        }

        return view;
    }
}
