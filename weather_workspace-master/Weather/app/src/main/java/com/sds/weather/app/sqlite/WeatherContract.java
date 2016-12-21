package com.sds.weather.app.sqlite;

import android.provider.BaseColumns;

/**
 * Created on 2016-11-30.
 */

public class WeatherContract {

    public static final class LocationEntry implements BaseColumns {
        public static final String TABLE_NAME = "LOCATION";
        public static final String COLUMN_SIDO = "SIDO";
        public static final String COLUMN_GUGUN = "GUGUN";
        public static final String COLUMN_DONG = "DONG";
        public static final String COLUMN_X = "X";
        public static final String COLUMN_Y = "Y";
    }
}
