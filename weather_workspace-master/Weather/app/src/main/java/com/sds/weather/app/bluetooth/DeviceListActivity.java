package com.sds.weather.app.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sds.weather.app.R;
import com.sds.weather.app.adapter.DeviceItemAdapter;

import java.io.IOException;
import java.util.UUID;

/**
 * Created on 2016-12-19.
 */

public class DeviceListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final int REQUEST_ENABLE_BT = 1001;
    private static final int REQUEST_LOCATION_PERMISSION = 2001;

    // View
    private ListView listView;
    private DeviceItemAdapter deviceItemAdapter;

    // Bluetooth
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket socket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // View
        listView = (ListView) findViewById(R.id.listView);
        deviceItemAdapter = new DeviceItemAdapter(this);
        listView.setAdapter(deviceItemAdapter);

        // Listener
        listView.setOnItemClickListener(this);

        // Bluetooth
        checkBluetoothSupport();
        requestBluetoothEnable();

        // Broadcast Receiver
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    deviceItemAdapter.updateAdapter(device);
                }
            }
        };

        // Intent Filter
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * Bluetooth 를 지원하는 기기인지 확인한다.
     */
    private void checkBluetoothSupport() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Bluetooth 활성화를 요청한다.
     */
    private void requestBluetoothEnable() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
        }

        scanDevice();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "블루투스를 활성화해야 합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    /**
     * Bluetooth 기기를 검색한다.
     */
    private void scanDevice() {
        if (!checkLocationPermission()) {
            return;
        }

        deviceItemAdapter.getDeviceList().clear();
        bluetoothAdapter.startDiscovery();
    }

    /**
     * 위치 권한을 확인하고 없으면 요청한다.
     */
    private boolean checkLocationPermission() {
        boolean result;

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            result = true;
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION
            );
            result = false;
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "위치권한을 수락해주세요.", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bluetooth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scan:
                scanDevice();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BluetoothDevice device = (BluetoothDevice) parent.getAdapter().getItem(position);

        bluetoothAdapter.cancelDiscovery();
        deviceItemAdapter.getDeviceList().clear();

        try {
            socket = device.createRfcommSocketToServiceRecord(UUID.randomUUID());
            new SocketConnectTask().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * BluetoothSocket 에 connect 하는 작업
     */
    class SocketConnectTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                socket.connect();
            } catch (IOException connectException) {
                Log.e(getClass().getName(), "BluetoothSocket 을 connect() 하는데 에러 발생");
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException closeException) {
                        Log.e(getClass().getName(), "BluetoothSocket 을 close() 하는데 에러 발생");
                    }
                }
            }

            // todo bluetooth 연결

            return null;
        }
    }
}
