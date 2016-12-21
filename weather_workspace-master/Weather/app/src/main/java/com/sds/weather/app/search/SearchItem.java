package com.sds.weather.app.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sds.weather.app.R;
import com.sds.weather.app.dto.Location;

/**
 * Created on 2016-12-02.
 */

public class SearchItem extends LinearLayout {
    private Context context;

    // View
    private TextView txt_location;

    // DTO
    private Location location;

    public SearchItem(Context context, Location location) {
        super(context);
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_search, this);

        // View
        txt_location = (TextView) findViewById(R.id.txt_location);

        initView(location);
    }

    /**
     * View 를 DTO의 내용으로 초기화 한다.
     */
    public void initView(Location location) {
        this.location = location;

        String sido = location.getSido();
        String gugun = location.getGugun();
        String dong = location.getDong();

        txt_location.setText(sido + " " + gugun + " " + dong);
    }
}
