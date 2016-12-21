package com.sds.weather.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.sds.weather.app.bluetooth.DeviceListActivity;
import com.sds.weather.app.item.CurrentWeatherItem;
import com.sds.weather.app.adapter.ForecastItemAdapter;
import com.sds.weather.app.commons.NetworkManager;
import com.sds.weather.app.task.FetchCurrentWeatherTask;
import com.sds.weather.app.task.FetchForecastTask;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public static final int REQUEST_CHANGE_LOCATION = 1001;

    // View
    private CurrentWeatherItem currentWeatherItem;
    private ListView listView;

    // Adapter
    private ForecastItemAdapter forecastItemAdapter;

    // SearchView
    private SearchView searchView;
    private MenuItem item_action_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // View
        currentWeatherItem = (CurrentWeatherItem) findViewById(R.id.currentWeatherItem);
        listView = (ListView) findViewById(R.id.listView);
        forecastItemAdapter = new ForecastItemAdapter(this);
        listView.setAdapter(forecastItemAdapter);

        initWeather();
    }

    /**
     * View 를 최신 데이터로 초기화한다.
     */
    public void initWeather() {
        boolean isOnline = new NetworkManager(this).isOnline();

        if (!isOnline) {
            Toast.makeText(this, "네트워크를 연결해 주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        // 현재 날씨 데이터와 예보 데이터를 가져온다.
        new FetchCurrentWeatherTask(this).execute();
        new FetchForecastTask(this).execute();

        Toast.makeText(this, "데이터 갱신 완료", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        item_action_change = menu.findItem(R.id.action_change);
        searchView = (SearchView) MenuItemCompat.getActionView(item_action_change);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.action_sync:
                initWeather();
                break;
            case R.id.action_bluetooth_connect:
                startActivity(new Intent(this, DeviceListActivity.class));
                break;
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        item_action_change.collapseActionView();

        Intent intent = new Intent(this, SearchableActivity.class);
        intent.putExtra("query", query);
        startActivityForResult(intent, REQUEST_CHANGE_LOCATION);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHANGE_LOCATION:
                if (resultCode == RESULT_OK) {
                    // 지역을 변경했다면 날씨를 갱신시킨다.
                    initWeather();
                }
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public CurrentWeatherItem getCurrentWeatherItem() {
        return currentWeatherItem;
    }

    public ForecastItemAdapter getForecastItemAdapter() {
        return forecastItemAdapter;
    }
}
