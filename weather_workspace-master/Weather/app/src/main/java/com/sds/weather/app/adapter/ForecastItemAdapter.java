package com.sds.weather.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sds.weather.app.dto.Forecast;
import com.sds.weather.app.item.ForecastItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eee on 2016-11-29.
 */

public class ForecastItemAdapter extends BaseAdapter {
    private Context context;

    private List<Forecast> forecastList = new ArrayList<>();

    public ForecastItemAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return forecastList.size();
    }

    @Override
    public Object getItem(int position) {
        return forecastList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Forecast forecast = forecastList.get(position);
        ForecastItem resultView = new ForecastItem(context, forecast);

        if (convertView != null) {
            resultView = (ForecastItem) convertView;
            resultView.initView(forecast);
        } else {
            resultView.initView(forecast);
        }

        return resultView;
    }

    public List<Forecast> getForecastList() {
        return forecastList;
    }
}
