package com.sds.study.sqliteapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 액티비티간 데이터 전달을 위해 현재 객체는 parcelable시키자
 */

public class Member implements Parcelable{
    private int member_id;
    private String id;
    private String password;

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.member_id);
        dest.writeString(this.id);
        dest.writeString(this.password);
    }

    public Member() {
    }

    protected Member(Parcel in) {
        this.member_id = in.readInt();
        this.id = in.readString();
        this.password = in.readString();
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel source) {
            return new Member(source);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };
}
