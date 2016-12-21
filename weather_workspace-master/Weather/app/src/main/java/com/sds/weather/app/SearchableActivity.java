package com.sds.weather.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sds.weather.app.dto.Location;
import com.sds.weather.app.search.SearchItemAdapter;
import com.sds.weather.app.sqlite.LocationDAO;

import java.util.List;

/**
 * Created on 2016-12-02.
 */

public class SearchableActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // SQLite
    private LocationDAO locationDAO;
    private List<Location> locationList;

    // View
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // SQLite
        locationDAO = new LocationDAO(this);
        String query = getIntent().getStringExtra("query");
        locationList = locationDAO.selectList(query);

        // AdapterView
        SearchItemAdapter searchItemAdapter = new SearchItemAdapter(this, locationList);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(searchItemAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putString(getString(R.string.pref_location_key), String.valueOf(id)).commit();
        Toast.makeText(this, "지역을 변경하였습니다", Toast.LENGTH_SHORT).show();

        // RESULT_OK 를 보내면 MainActivity가 새로고침한다.
        setResult(RESULT_OK);
        finish();
    }
}
