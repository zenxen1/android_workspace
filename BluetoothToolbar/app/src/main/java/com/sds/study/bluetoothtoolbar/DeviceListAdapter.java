package com.sds.study.bluetoothtoolbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lee on 2016-11-22.
 */

public class DeviceListAdapter extends BaseAdapter {
    ArrayList<Device>list = new ArrayList<Device>();
    LayoutInflater inflater;
    Context context;

    public DeviceListAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        /*6.인플레이션 일어나야함*/
        View view=null;
        Device dto = list.get(i);

        if(convertView==null){
            //지금 새로 인플레이션하여 아이템 새로 생성
            view = inflater.inflate(R.layout.device_item,null);
        }else {
            //그냥 그대로 사용할 거야..내용만 바꿔서
            view = convertView;
        }
        TextView txt_name = (TextView) view.findViewById(R.id.txt_name);
        TextView txt_adress = (TextView) view.findViewById(R.id.txt_adress);
        txt_name.setText(dto.getName());
        txt_adress.setText(dto.getAddress());

        return view;
    }
}
