package com.sds.weather.app.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sds.weather.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016-12-19.
 */

public class DeviceItemAdapter extends BaseAdapter {

    private List<BluetoothDevice> deviceList;
    private LayoutInflater inflater;

    public DeviceItemAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        deviceList = new ArrayList<>();
    }

    public void updateAdapter(BluetoothDevice device) {
        deviceList.add(device);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return deviceList.size();
    }

    @Override
    public BluetoothDevice getItem(int position) {
        return deviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BluetoothDevice device = deviceList.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_device, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.txt_address = (TextView) convertView.findViewById(R.id.txt_address);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txt_name.setText(device.getName());
        viewHolder.txt_address.setText(device.getAddress());

        return convertView;
    }

    public List<BluetoothDevice> getDeviceList() {
        return deviceList;
    }

    static class ViewHolder {
        TextView txt_name;
        TextView txt_address;
    }
}
