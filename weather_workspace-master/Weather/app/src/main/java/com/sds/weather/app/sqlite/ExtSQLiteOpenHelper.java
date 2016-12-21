package com.sds.weather.app.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sds.weather.app.data.CsvReader;
import com.sds.weather.app.sqlite.WeatherContract.LocationEntry;

import org.apache.commons.csv.CSVRecord;

import java.util.List;

/**
 * Created on 2016-11-30.
 */

public class ExtSQLiteOpenHelper extends SQLiteOpenHelper {
    // Singleton
    private static ExtSQLiteOpenHelper instance;

    private Context context;

    // CSV
    private CsvReader csvReader;
    private List<CSVRecord> records;

    // DB
    public static final String DB_NAME = "weather.sqlite";
    public static final int DB_VERSION = 1;

    private ExtSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

        // CSV
        csvReader = new CsvReader(context);
        records = csvReader.readCSV();
    }

    public static synchronized ExtSQLiteOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ExtSQLiteOpenHelper(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(getClass().getName(), "onCreate() called");

        final String SQL_CREATE_WEATHER_TABLE =
                "CREATE TABLE " + LocationEntry.TABLE_NAME + " (" +
                        LocationEntry._ID + " INTEGER PRIMARY KEY, " +
                        LocationEntry.COLUMN_SIDO + " TEXT, " +
                        LocationEntry.COLUMN_GUGUN + " TEXT, " +
                        LocationEntry.COLUMN_DONG + " TEXT, " +
                        LocationEntry.COLUMN_X + " INTEGER, " +
                        LocationEntry.COLUMN_Y + " INTEGER);";

        try {
            db.execSQL(SQL_CREATE_WEATHER_TABLE);
            Log.d(getClass().getName(), "WEATHER TABLE 을 CREATE 함");
        } catch (SQLException e) {
            Log.e(getClass().getName(), "WEATHER TABLE 을 CREATE 하지 못했습니다." + e.getMessage());
        }

        // CSV 파일 넣기
        insertCsvList(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertCsvList(SQLiteDatabase db) {
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            for (CSVRecord record : records) {
                values.put(LocationEntry.COLUMN_SIDO, record.get(0));
                values.put(LocationEntry.COLUMN_GUGUN, record.get(1));
                values.put(LocationEntry.COLUMN_DONG, record.get(2));
                values.put(LocationEntry.COLUMN_X, record.get(3));
                values.put(LocationEntry.COLUMN_Y, record.get(4));

                db.insertOrThrow(LocationEntry.TABLE_NAME, null, values);
            }

            db.setTransactionSuccessful();
            Log.d(getClass().getName(), "CSV 파일을 성공적으로 INSERT 함");
        } catch (SQLException e) {
            Log.e(getClass().getName(), "CSV 파일을 INSERT 하는 중 에러가 발생 " + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }
}
