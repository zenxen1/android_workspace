package com.sds.weather.app.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sds.weather.app.dto.Location;
import com.sds.weather.app.sqlite.WeatherContract.LocationEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016-11-30.
 */

public class LocationDAO {
    private Context context;

    // SQLite
    private SQLiteDatabase db;

    public LocationDAO(Context context) {
        this.context = context;
        db = ExtSQLiteOpenHelper.getInstance(context).getWritableDatabase();
    }

    public int insert(Location location) {
        return 0;
    }

    /**
     * 해당 _id 를 가지고 있는 Location 을 찾는다.
     *
     * @param location_pk 기본키
     * @return 해당되는 DTO
     */
    public Location selectOne(int location_pk) {
        Location location = null;

        String sql = "SELECT * FROM " + LocationEntry.TABLE_NAME +
                " WHERE " + LocationEntry._ID + " = ? ";

        Cursor cursor = db.rawQuery(
                sql,
                new String[]{Integer.toString(location_pk)}
        );

        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(LocationEntry._ID));
            String sido = cursor.getString(cursor.getColumnIndex(LocationEntry.COLUMN_SIDO));
            String gugun = cursor.getString(cursor.getColumnIndex(LocationEntry.COLUMN_GUGUN));
            String dong = cursor.getString(cursor.getColumnIndex(LocationEntry.COLUMN_DONG));
            int x = cursor.getInt(cursor.getColumnIndex(LocationEntry.COLUMN_X));
            int y = cursor.getInt(cursor.getColumnIndex(LocationEntry.COLUMN_Y));

            location = new Location(_id, sido, gugun, dong, x, y);
            Log.d(getClass().getName(), "location = " + location);
        }

        return location;
    }

    /**
     * 검색어에 따라서 지역을 검색한다.
     *
     * @param keyword 검색할 문자열
     * @return 검색 결과 List
     */
    public List<Location> selectList(String keyword) {
        List<Location> locationList = new ArrayList<>();

        String sql = "SELECT * FROM " + LocationEntry.TABLE_NAME +
                " WHERE " + LocationEntry.COLUMN_SIDO + " LIKE " + "\"%" + keyword + "%\"" +
                " OR " + LocationEntry.COLUMN_GUGUN + " LIKE " + "\"%" + keyword + "%\"" +
                " OR " + LocationEntry.COLUMN_DONG + " LIKE " + "\"%" + keyword + "%\"";
        Log.d(getClass().getName(), "sql = " + sql);

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(LocationEntry._ID));
            String sido = cursor.getString(cursor.getColumnIndex(LocationEntry.COLUMN_SIDO));
            String gugun = cursor.getString(cursor.getColumnIndex(LocationEntry.COLUMN_GUGUN));
            String dong = cursor.getString(cursor.getColumnIndex(LocationEntry.COLUMN_DONG));
            int x = cursor.getInt(cursor.getColumnIndex(LocationEntry.COLUMN_X));
            int y = cursor.getInt(cursor.getColumnIndex(LocationEntry.COLUMN_Y));

            Location location = new Location(_id, sido, gugun, dong, x, y);
            locationList.add(location);
        }

        return locationList;
    }

    /**
     * 모든 데이터를 검색한다.
     *
     * @return
     */
    public List<Location> selectAll() {
        List<Location> locationList = new ArrayList<>();

        String sql = "SELECT * FROM " + LocationEntry.TABLE_NAME;
        Log.d(getClass().getName(), "sql = " + sql);

        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            int _id = cursor.getInt(cursor.getColumnIndex(LocationEntry._ID));
            String sido = cursor.getString(cursor.getColumnIndex(LocationEntry.COLUMN_SIDO));
            String gugun = cursor.getString(cursor.getColumnIndex(LocationEntry.COLUMN_GUGUN));
            String dong = cursor.getString(cursor.getColumnIndex(LocationEntry.COLUMN_DONG));
            int x = cursor.getInt(cursor.getColumnIndex(LocationEntry.COLUMN_X));
            int y = cursor.getInt(cursor.getColumnIndex(LocationEntry.COLUMN_Y));

            Location location = new Location(_id, sido, gugun, dong, x, y);
            locationList.add(location);
        }

        return locationList;
    }

    public int update() {
        return 0;
    }

    public int delete() {
        return 0;
    }
}
