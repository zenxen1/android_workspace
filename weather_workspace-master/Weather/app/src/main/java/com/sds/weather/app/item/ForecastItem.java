package com.sds.weather.app.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sds.weather.app.R;
import com.sds.weather.app.commons.WeatherImgParser;
import com.sds.weather.app.dto.Forecast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by eee on 2016-11-29.
 */

public class ForecastItem extends LinearLayout {
    private Context context;
    private Forecast forecast;

    // View
    private TextView txt_fcstDate;
    private ImageView img_sky;
    private TextView txt_pop;

    public ForecastItem(Context context, Forecast forecast) {
        super(context);
        this.context = context;
        this.forecast = forecast;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_list_forecast, this);

        txt_fcstDate = (TextView) findViewById(R.id.txt_fcstDate);
        img_sky = (ImageView) findViewById(R.id.img_sky);
        txt_pop = (TextView) findViewById(R.id.txt_pop);

        initView(forecast);
    }

    /**
     * View 의 내용을 Forecast DTO 의 내용으로 초기화한다.
     */
    public void initView(Forecast forecast) {
        this.forecast = forecast;

        String fcstDate = forecast.getFcstDate().substring(6, 8);
        String fcstHour = forecast.getFcstTime().substring(0, 2);
        String fcstMinute = forecast.getFcstTime().substring(2, 4);

        Calendar fcstDateCalendar = Calendar.getInstance();
        fcstDateCalendar.set(Calendar.DATE, Integer.parseInt(fcstDate));
        fcstDateCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(fcstHour));
        fcstDateCalendar.set(Calendar.MINUTE, Integer.parseInt(fcstMinute));

        Date fcstDateTime = fcstDateCalendar.getTime();
        String fcstDateStr = new SimpleDateFormat("HH시 mm분").format(fcstDateTime);

        // 날짜 설정
        txt_fcstDate.setText(fcstDateStr);

        // 날씨 아이콘 설정
        int pty = forecast.getPtyValue();
        int sky = forecast.getSkyValue();

        WeatherImgParser weatherImgParser = new WeatherImgParser();
        int img = weatherImgParser.parseWeatherData(pty, sky, 0, fcstDateTime);
        img_sky.setImageResource(img);

        // 강수 확률
        txt_pop.setText(Integer.toString((int) forecast.getPopValue()) +"%");
    }
}
