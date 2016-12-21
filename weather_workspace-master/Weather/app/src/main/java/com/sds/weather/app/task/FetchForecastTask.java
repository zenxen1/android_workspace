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
import com.sds.weather.app.adapter.ForecastItemAdapter;
import com.sds.weather.app.dto.Forecast;
import com.sds.weather.app.dto.Location;
import com.sds.weather.app.sqlite.LocationDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by eee on 2016-11-29.
 */

public class FetchForecastTask extends AsyncTask<Void, Void, List<Forecast>> {
    private enum Category {
        POP, PTY, R06, REH, S06, SKY, T3H,
        TMN, TMX, UUU, VVV, WAV, VEC, WSD
    }

    private float tmn;
    private float tmx;

    private MainActivity mainActivity;
    private ForecastItemAdapter forecastItemAdapter;
    private CurrentWeatherItem currentWeatherItem;

    // SQLite
    private LocationDAO locationDAO;

    public FetchForecastTask(Context context) {
        mainActivity = (MainActivity) context;
        forecastItemAdapter = mainActivity.getForecastItemAdapter();
        currentWeatherItem = mainActivity.getCurrentWeatherItem();

        locationDAO = new LocationDAO(context);
    }

    @Override
    protected List<Forecast> doInBackground(Void... params) {

        String json = requestForecast();

        return parseJson(json);
    }

    @Override
    protected void onPostExecute(List<Forecast> forecastList) {
        Log.d(getClass().getName(), "forecastList = " + forecastList);

        // forecastItemAdapter 갱신
        forecastItemAdapter.getForecastList().removeAll(forecastItemAdapter.getForecastList());
        forecastItemAdapter.getForecastList().addAll(forecastList);
        forecastItemAdapter.notifyDataSetChanged();

        // CurrentWeaterItem 갱신
        currentWeatherItem.initTmnTmx(tmn, tmx);
    }

    /**
     * 현재 시각에 알맞는 baseDate 와 baseTime 을 구한다.
     *
     * @param today 기준이 되는 날짜
     * @return 알맞는 baseDate 와 baseTime 가 합쳐진 Date 객체
     */
    private Date getBaseDate(Date today) {
        int[] baseTimeArr = new int[]{2, 5, 8, 11, 14, 17, 20, 23};
        Date[] tempBaseDateArr = new Date[baseTimeArr.length];
        Date[] baseDateArr = new Date[baseTimeArr.length];

        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < baseTimeArr.length; i++) {
            calendar.set(Calendar.HOUR_OF_DAY, baseTimeArr[i]);
            calendar.set(Calendar.MINUTE, 31);      // 생성 시간이 base 시간 + 30 분이다. 만약을 위해 1분의 여유를 준다.
            calendar.set(Calendar.SECOND, 0);

            tempBaseDateArr[i] = calendar.getTime();

            calendar.set(Calendar.MINUTE, 0);
            baseDateArr[i] = calendar.getTime();
        }

        Date base_date = null;

        for (int i = 0; i < tempBaseDateArr.length; i++) {
            if (today.before(tempBaseDateArr[0])) {     // 02 시 30분 이전일 경우
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                base_date = cal.getTime();
                break;
            } else if (today.after(tempBaseDateArr[tempBaseDateArr.length - 1])) {     // 23시 30분 이후일 경우
                base_date = baseDateArr[baseDateArr.length - 1];
                break;
            } else if (today.after(tempBaseDateArr[i]) && today.before(tempBaseDateArr[i + 1])) {    // 02시 30분과 23시 30분 사이일 경우
                base_date = baseDateArr[i];
                break;
            }
        }

        return base_date;
    }

    /**
     * 기상청으로부터 예보 데이터를 가져온다.
     *
     * @return JSON 문자열을 반환한다.
     */
    private String requestForecast() {
        URL url;
        HttpURLConnection conn = null;
        BufferedReader bufferedReader = null;
        StringBuilder json = new StringBuilder();

        // BASE_DATE 와 BASE_TIME 설정
        Date baseDate = getBaseDate(new Date());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        String location_pk = preferences.getString(mainActivity.getString(R.string.pref_location_key), mainActivity.getString(R.string.pref_location_default));
        Location location = locationDAO.selectOne(Integer.parseInt(location_pk));

        final String FORECAST_BASE_URL = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData?";

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
        final String VALUE_NUM_OF_ROWS = "82";
        final String VALUE_TYPE = "json";

        Uri uriBuilt = Uri.parse(FORECAST_BASE_URL).buildUpon()
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
    private List<Forecast> parseJson(String json) {
        List<Forecast> forecastList = new ArrayList<>();
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

        String prevFcstTime = "9999";
        Forecast forecast = null;

        for (int i = 0; i < item.size(); i++) {
            JsonObject forecastObj = (JsonObject) item.get(i);
            String baseDate = forecastObj.get("baseDate").getAsString();
            String baseTime = forecastObj.get("baseTime").getAsString();
            int nx = forecastObj.get("nx").getAsInt();
            int ny = forecastObj.get("ny").getAsInt();

            // 각 Item 마다 반복
            String category = forecastObj.get("category").getAsString();
            String fcstDate = forecastObj.get("fcstDate").getAsString();
            String fcstTime = forecastObj.get("fcstTime").getAsString();
            float fcstValue = forecastObj.get("fcstValue").getAsFloat();

            if (!fcstTime.equals(prevFcstTime)) {   // 이전 아이템의 fcstTime 과 비교
                prevFcstTime = fcstTime;

                if (forecast != null) {
                    // 이전과 다르면서 forecast가 null 이 아니면 기존 forecast 를 list에 담고 새로 생성한다.
                    forecastList.add(forecast);
                }

                // 이전과 다르면 새로운 Forecast 를 생성하고 각 데이터를 담는다.
                forecast = new Forecast();
                forecast.setBaseDate(baseDate);
                forecast.setBaseTime(baseTime);
                forecast.setNx(nx);
                forecast.setNy(ny);
                forecast.setFcstDate(fcstDate);
                forecast.setFcstTime(fcstTime);
            }

            // 같든 다르든 fcsvValue 의 값을 채워준다.
            if (forecast != null) {
                Category enumCategory = null;

                try {
                    enumCategory = Category.valueOf(category);
                } catch (IllegalArgumentException e) {
                    Log.e(getClass().getName(), "Category enum 에 선언되지 않은 문자열입니다. " + e.getMessage());
                }

                switch (enumCategory) {
                    case POP:
                        forecast.setPopValue(fcstValue);
                        break;
                    case PTY:
                        forecast.setPtyValue(fcstValue);
                        break;
                    case REH:
                        forecast.setRehValue(fcstValue);
                        break;
                    case SKY:
                        forecast.setSkyValue(fcstValue);
                        break;
                    case T3H:
                        forecast.setT3hValue(fcstValue);
                        break;
                    case UUU:
                        forecast.setUuuValue(fcstValue);
                        break;
                    case VEC:
                        forecast.setVecValue(fcstValue);
                        break;
                    case VVV:
                        forecast.setVvvValue(fcstValue);
                        break;
                    case WSD:
                        forecast.setWsdValue(fcstValue);
                        break;
                    case R06:
                        forecast.setR06Value(fcstValue);
                        break;
                    case S06:
                        forecast.setS06Value(fcstValue);
                        break;
                    case TMN:
                        forecast.setTmnValue(fcstValue);
                        tmn = fcstValue;
                        break;
                    case TMX:
                        forecast.setTmxValue(fcstValue);
                        tmx = fcstValue;
                        break;
                    case WAV:
                        forecast.setWavValue(fcstValue);
                        break;
                }
            }
        }

        return forecastList;
    }
}
