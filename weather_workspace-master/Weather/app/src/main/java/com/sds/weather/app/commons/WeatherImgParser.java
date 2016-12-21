package com.sds.weather.app.commons;

import com.sds.weather.app.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eee on 2016-12-02.
 */

public class WeatherImgParser {
    /**
     * 하늘, 강수, 낙뢰 정보를 주면 알맞은 이미지를 반환한다.
     *
     * @return 반환되는 이미지
     */
    public int parseWeatherData(int pty, int sky, int lgt, Date fcstDate) {
        boolean isDay = false;

        int fcstHHmm = Integer.parseInt(new SimpleDateFormat("HHmm").format(fcstDate));
        if (fcstHHmm < 600 || fcstHHmm >= 1800) {  // 밤일 경우
            isDay = false;
        } else if (fcstHHmm >= 600 && fcstHHmm < 1800) {   // 낮일 경우
            isDay = true;
        }

        if (lgt > 0) {     // 낙뢰 아이콘 처리
            if (pty == 0) {
                return R.drawable.lightening;
            } else {
                return R.drawable.rain_lightening;
            }
        } else if (pty > 0) {      // 비가 올 때 강수 아이콘 처리
            if (pty == 1) {
                return R.drawable.rain;
            } else if (pty == 2) {
                return R.drawable.rain_snow;
            } else if (pty == 3) {
                return R.drawable.snow;
            }
        } else if (sky > 0) {
            if (sky == 2) {
                return R.drawable.littlecloud;
            } else if (sky == 3 || sky == 4) {
                return R.drawable.cloud;
            }
        } else {
            if (isDay) {
                return R.drawable.sun;
            } else {
                return R.drawable.night;
            }
        }

        return R.drawable.sun;
    }
}
