package com.sds.study.bluetoothclient;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by student on 2016-11-21.
 */

public class ListAdapter extends BaseAdapter {
    Context context;
    ArrayList <Device> list = new <Device>ArrayList();
    public ListAdapter(Context context) {
        this.context=context;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int i) {
        return list.get(i);
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View converView, ViewGroup viewGroup) {
        View view=null;
        Device dto = list.get(i);
        /*이미 채워진 아이템이 있는경우*/
        if(converView!=null) {
            view=converView;
            ((DeviceItem)view).init(dto);
        }else{
        /*아무것도 채워지지않은 경우*/
            view = new DeviceItem(context,dto);
        }
        return view;
    }
}
