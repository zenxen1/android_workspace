package com.sds.study.adapterviewtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by student on 2016-11-10.
 */

public class MyAdapter extends BaseAdapter {
    Context context;

    //총 아이템 갯수!!
    String[] arr={"사과","딸기","오렌지","망고","멜론","배","바나나"};
    String[] arr_phone={"111","111","111","111","111","111","111","111"};
    int[] photo={
            R.drawable.batman,
            R.drawable.captain,
            R.drawable.flashman,
            R.drawable.green,
            R.drawable.ironman,
            R.drawable.punisher,
            R.drawable.robin,
            R.drawable.spiderman,
            R.drawable.superman,
            R.drawable.wonder
    };

    public MyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return arr.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = null;
        inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        LinearLayout itemLayout=(LinearLayout)inflater.inflate(R.layout.hero_item,null);
        TextView txt_name = (TextView)itemLayout.findViewById(R.id.tx_name);
        TextView txt_phone = (TextView)itemLayout.findViewById(R.id.tx_phone);
        ImageView img = (ImageView)itemLayout.findViewById(R.id.img);

        txt_name.setText(arr[i]);
        txt_phone.setText(arr_phone[i]);
        img.setImageResource(photo[i]);

        return itemLayout;
    }
    /*public View getView(int i, View view, ViewGroup viewGroup) {
        Button bt = new Button(context);
        bt.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        bt.setText(arr[i]);

        return bt;
    }*/
}
