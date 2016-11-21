package com.sds.study.bluetoothclient;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter;/*4.장치제어*/
    static final int REQUEST_BLUETOOTH_ENABLE=1;
    static final int REQUEST_ACCESS_PERMISSION=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkSupportBluetooth();
        requstActiveBluetooth();
    }
    /*1장치 지원 여부 체크*/
    public void checkSupportBluetooth(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter==null){
            showMsg("안내","디바이스가 블루투스를 지원하지 않는다");
            finish();
        }
    }
    /*2꺼있다면 활성화 요청*/
    public void requstActiveBluetooth(){
        Intent intent = new Intent();
        intent.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent,REQUEST_BLUETOOTH_ENABLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_BLUETOOTH_ENABLE:
                if(resultCode==RESULT_CANCELED){
                    showMsg("안내","블루투스 사용 가능");
                }
        }
    }

    /*3.8허락여부를 판다*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_ACCESS_PERMISSION:
                if(permissions.length>0&&grantResults[0]==PackageManager.PERMISSION_DENIED){
                    showMsg("안내","권한을 부여해 주세요");
                }
        }
    }

    public void checkAccessPermission(){
        /*3.5 로케이션 권한 체크 4.xx 초과 버전에서는 이 권한이 필요하다더라...*/
        int accessPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if(accessPermission== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION
            },REQUEST_ACCESS_PERMISSION);
        }else {
            scanDevice();
        }
    }

    /*3.주변에 가까운 디바이스를 검색한다..*/
    public void scanDevice(){




        /*시스템의 브로드 케스팅을 낚아채서 알맞는 처리..*/
        BroadcastReceiver receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                /*7.수많은 방송 내용중 우리가 등록한 Action_found에 대해서처리한다*/
                String action = intent.getAction();
                switch(action){
                    case BluetoothDevice.ACTION_FOUND:
                        Toast.makeText(getApplicationContext(),"발견했어요",Toast.LENGTH_SHORT).show();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(receiver,filter);
        /*6.검색을 시작하시오*/
        bluetoothAdapter.startDiscovery();

    }

    public void btnClick(View view){
        if(view.getId()==R.id.bt_scan){
            checkAccessPermission();
        }else if(view.getId()==R.id.bt_send){

        }
    }
    /*5.메세지출력*/
    public void showMsg(String title,String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title).setMessage(message).show();
    }
}
