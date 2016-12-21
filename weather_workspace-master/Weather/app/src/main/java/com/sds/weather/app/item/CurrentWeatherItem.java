package com.sds.weather.app.item;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sds.weather.app.R;
import com.sds.weather.app.commons.WeatherImgParser;
import com.sds.weather.app.dto.CurrentWeather;
import com.sds.weather.app.dto.Location;
import com.sds.weather.app.sqlite.LocationDAO;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 2016-12-01.
 */

public class CurrentWeatherItem extends LinearLayout {
    private Context context;

    // DTO
    private CurrentWeather currentWeather;

    // View
    private TextView txt_location;
    private TextView txt_currentTime;
    private ImageView img_weather;
    private TextView txt_t1h;
    private TextView txt_reh;
    private TextView txt_wsd;
    private TextView txt_tmn;
    private TextView txt_tmx;

    // SQLite
    private LocationDAO locationDAO;

    public CurrentWeatherItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        locationDAO = new LocationDAO(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_current_weather, this);

        txt_location = (TextView) findViewById(R.id.txt_location);
        txt_currentTime = (TextView) findViewById(R.id.txt_currentTime);
        img_weather = (ImageView) findViewById(R.id.img_weather);
        txt_t1h = (TextView) findViewById(R.id.txt_t1h);
        txt_reh = (TextView) findViewById(R.id.txt_reh);
        txt_wsd = (TextView) findViewById(R.id.txt_wsd);
        txt_tmn = (TextView) findViewById(R.id.txt_tmn);
        txt_tmx = (TextView) findViewById(R.id.txt_tmx);
    }

    public void initView(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        // 위치
        String locationValue = preferences.getString(context.getString(R.string.pref_location_key), context.getString(R.string.pref_location_default));

        Location locationDTO = locationDAO.selectOne(Integer.parseInt(locationValue));
        String location;
        if (!locationDTO.getDong().equals("")) {
            location = locationDTO.getDong();
        } else if (!locationDTO.getGugun().equals("")) {
            location = locationDTO.getGugun();
        } else {
            location = locationDTO.getSido();
        }
        txt_location.setText(location);

        // 날짜
        String currentTime = new SimpleDateFormat("MM월 dd일 E요일 HH시 mm분").format(new Date());
        txt_currentTime.setText(currentTime);

        // 온도
        String unit = preferences.getString(context.getString(R.string.pref_unit_key), context.getString(R.string.pref_unit_default));
        float t1h = currentWeather.getT1hValue();
        if (unit.equals(context.getString(R.string.pref_unit_fahrenheit))) {
            t1h = (float) ((9.0 / 5.0) * t1h + 32);
        }
        txt_t1h.setText(t1h + unit);

        // 습도 풍속
        txt_reh.setText(currentWeather.getRehValue() + "%");
        txt_wsd.setText(currentWeather.getWsdValue() + "m/s");

        // 날씨 아이콘
        initWeatherImg(currentWeather);
    }

    /**
     * 최저, 최고기온을 설정한다.
     */
    public void initTmnTmx(float tmn, float tmx) {
        txt_tmn.setText(Float.toString(tmn));
        txt_tmx.setText(Float.toString(tmx));
    }

    /**
     * 날씨 아이콘을 초기화한다.
     */
    public void initWeatherImg(CurrentWeather currentWeather) {
        int sky = currentWeather.getSkyValue();
        int pty = currentWeather.getPtyValue();
        int lgt = currentWeather.getLgtValue();

        WeatherImgParser weatherImgParser = new WeatherImgParser();
        int img = weatherImgParser.parseWeatherData(pty, sky, lgt, new Date());
        img_weather.setImageResource(img);
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }
}
