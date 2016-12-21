package com.sds.weather.app.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sds.weather.app.MainActivity;
import com.sds.weather.app.R;
import com.sds.weather.app.item.CurrentWeatherItem;
import com.sds.weather.app.dto.CurrentWeather;
import com.sds.weather.app.dto.Location;
import com.sds.weather.app.sqlite.LocationDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by eee on 2016-11-30.
 */

public class FetchCurrentWeatherTask extends AsyncTask<Void, Void, CurrentWeather> {
    private enum Category {
        T1H, RN1, SKY, UUU, VVV,
        REH, PTY, LGT, VEC, WSD
    }

    private MainActivity mainActivity;
    private CurrentWeatherItem currentWeatherItem;

    // SQLite
    private LocationDAO locationDAO;

    public FetchCurrentWeatherTask(Context context) {
        mainActivity = (MainActivity) context;
        currentWeatherItem = mainActivity.getCurrentWeatherItem();

        locationDAO = new LocationDAO(context);
    }

    @Override
    protected CurrentWeather doInBackground(Void... params) {
        String json = requestCurrentWeather();

        return parseJson(json);
    }

    @Override
    protected void onPostExecute(CurrentWeather currentWeather) {
        currentWeatherItem.initView(currentWeather);
    }

    /**
     * 현재 시각에 알맞는 baseDate 와 baseTime 을 구한다.
     *
     * @param today 기준이 되는 날짜
     * @return 알맞는 baseDate 와 baseTime 가 합쳐진 Date 객체
     */
    private Date getBaseDate(Date today) {
        int[] baseTimeArr = new int[]{
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                20, 21, 22, 23
        };
        Date[] tempBaseDateArr = new Date[baseTimeArr.length];
        Date[] baseDateArr = new Date[baseTimeArr.length];

        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < baseTimeArr.length; i++) {
            calendar.set(Calendar.HOUR_OF_DAY, baseTimeArr[i]);
            calendar.set(Calendar.MINUTE, 31);
            calendar.set(Calendar.SECOND, 0);

            tempBaseDateArr[i] = calendar.getTime();

            calendar.set(Calendar.MINUTE, 0);
            baseDateArr[i] = calendar.getTime();
        }

        Date base_date = null;

        for (int i = 0; i < tempBaseDateArr.length; i++) {
            if (today.before(tempBaseDateArr[0])) {      // 00시 30분 이전일 경우
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                base_date = cal.getTime();
            } else if (today.after(tempBaseDateArr[tempBaseDateArr.length - 1])) {     // 23시 30분 이후일 경우
                base_date = baseDateArr[baseDateArr.length - 1];
                break;
            } else if (today.after(tempBaseDateArr[i]) && today.before(tempBaseDateArr[i + 1])) {    // 00시 30분과 23시 30분 사이일 경우
                base_date = baseDateArr[i];
                break;
            }
        }

        return base_date;
    }

    /**
     * 기상청으로부터 현재 기상 데이터를 가져온다.
     *
     * @return JSON 문자열
     */
    private String requestCurrentWeather() {
        URL url;
        HttpURLConnection conn = null;
        BufferedReader bufferedReader = null;
        StringBuilder json = new StringBuilder();

        // BASE_DATE 와 BASE_TIME 설정
        Date baseDate = getBaseDate(new Date());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        String location_pk = preferences.getString(mainActivity.getString(R.string.pref_location_key), mainActivity.getString(R.string.pref_location_default));
        Location location = locationDAO.selectOne(Integer.parseInt(location_pk));


        final String CURRENT_WEATHER_BASE_URL = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib?";

        final String PARAM_SERVICE_KEY = "ServiceKey";
        final String PARAM_BASE_DATE = "base_date";
        final String PARAM_BASE_TIME = "base_time";
        final String PARAM_NX = "nx";
        final String PARAM_NY = "ny";
        final String PARAM_NUM_OF_ROWS = "numOfRows";
        final String PARAM_TYPE = "_type";

        final String VALUE_SERVICE_KEY = "Xh0KCUo1p/pDbAPCb94AWn34HZebw2wp0g2kU0BaS3a2POCRTPwgufuhyKEhSH7nKr7LpDQyOu1GxeAhX7iH4g==";
        final String VALUE_BASE_DATE = new SimpleDateFormat("yyyyMMdd").format(baseDate);
        final String VALUE_BASE_TIME = new SimpleDateFormat("HHmm").format(baseDate);
        final String VALUE_NX = String.valueOf(location.getX());
        final String VALUE_NY = String.valueOf(location.getY());
        final String VALUE_NUM_OF_ROWS = "12";
        final String VALUE_TYPE = "json";

        Uri uriBuilt = Uri.parse(CURRENT_WEATHER_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SERVICE_KEY, VALUE_SERVICE_KEY)
                .appendQueryParameter(PARAM_BASE_DATE, VALUE_BASE_DATE)
                .appendQueryParameter(PARAM_BASE_TIME, VALUE_BASE_TIME)
                .appendQueryParameter(PARAM_NX, VALUE_NX)
                .appendQueryParameter(PARAM_NY, VALUE_NY)
                .appendQueryParameter(PARAM_NUM_OF_ROWS, VALUE_NUM_OF_ROWS)
                .appendQueryParameter(PARAM_TYPE, VALUE_TYPE)
                .build();

        try {
            Log.d(getClass().getName(), uriBuilt.toString());
            url = new URL(uriBuilt.toString());
            conn = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                json.append(temp);
            }
        } catch (MalformedURLException e) {
            Log.e(getClass().getName(), "올바르지 않은 URL 형식입니다. / " + e.getMessage());
        } catch (IOException e) {
            Log.e(getClass().getName(), "웹서버에 연결하는 과정에서 네트워크 문제가 발생했습니다. / " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return json.toString();
    }

    /**
     * JSON 형식을 분석하여 forecastList 에 넣는다.
     */
    private CurrentWeather parseJson(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonArray item = null;

        JsonElement jsonElement = jsonParser.parse(json);
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonObject response = jsonObject.getAsJsonObject("response");
            JsonObject body = response.getAsJsonObject("body");
            JsonObject items = body.getAsJsonObject("items");
            item = items.getAsJsonArray("item");
        }

        CurrentWeather currentWeather = new CurrentWeather();
        for (int i = 0; i < item.size(); i++) {
            JsonObject currentWeatherObj = (JsonObject) item.get(i);

            // 각 Item 마다 반복
            String category = currentWeatherObj.get("category").getAsString();
            float obsrValue = currentWeatherObj.get("obsrValue").getAsFloat();

            if (i == 0) {   // 0번째 일 때 한번만 수행한다.
                String baseDate = currentWeatherObj.get("baseDate").getAsString();
                String baseTime = currentWeatherObj.get("baseTime").getAsString();
                int nx = currentWeatherObj.get("nx").getAsInt();
                int ny = currentWeatherObj.get("ny").getAsInt();

                // 이전과 다르면 새로운 currentWeather 를 생성하고 각 데이터를 담는다.
                currentWeather.setBaseDate(baseDate);
                currentWeather.setBaseTime(baseTime);
                currentWeather.setNx(nx);
                currentWeather.setNy(ny);
            }

            // 같든 다르든 obsrValue 의 값을 채워준다.
            Category enumCategory = null;

            try {
                enumCategory = Category.valueOf(category);
            } catch (IllegalArgumentException e) {
                Log.e(getClass().getName(), "Category enum 에 선언되지 않은 문자열입니다. " + e.getMessage());
            }

            switch (enumCategory) {
                case T1H:
                    currentWeather.setT1hValue(obsrValue);
                    break;
                case RN1:
                    currentWeather.setRn1Value(obsrValue);
                    break;
                case SKY:
                    currentWeather.setSkyValue(obsrValue);
                    break;
                case UUU:
                    currentWeather.setUuuValue(obsrValue);
                    break;
                case VVV:
                    currentWeather.setVvvValue(obsrValue);
                    break;
                case REH:
                    currentWeather.setRehValue(obsrValue);
                    break;
                case PTY:
                    currentWeather.setPtyValue(obsrValue);
                    break;
                case LGT:
                    currentWeather.setLgtValue(obsrValue);
                    break;
                case VEC:
                    currentWeather.setVecValue(obsrValue);
                    break;
                case WSD:
                    currentWeather.setWsdValue(obsrValue);
                    break;
            }
        }

        return currentWeather;
    }
}
