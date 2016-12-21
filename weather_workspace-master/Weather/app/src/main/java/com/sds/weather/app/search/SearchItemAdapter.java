package com.sds.weather.app.search;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sds.weather.app.dto.Location;

import java.util.List;

/**
 * Created on 2016-12-02.
 */

public class SearchItemAdapter extends BaseAdapter {

    private Context context;
    private List<Location> locationList;

    public SearchItemAdapter(Context context, List<Location> locationList) {
        this.context = context;
        this.locationList = locationList;
    }

    @Override
    public int getCount() {
        return locationList.size();
    }

    @Override
    public Object getItem(int position) {
        return locationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return locationList.get(position).getLocation_pk();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Location location = locationList.get(position);
        SearchItem resultView = null;

        if (resultView != null) {
            resultView = (SearchItem) convertView;
            resultView.initView(location);
        } else {
            resultView = new SearchItem(context, location);
        }

        return resultView;
    }
}
