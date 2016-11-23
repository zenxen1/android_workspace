package com.sds.study.bluetoothtoolbar;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    Toolbar toolbar;
    BluetoothAdapter bluetoothAdapter;
    static final int REQUEST_BLUETOOTH_ENABLE = 1;
    static final int REQUEST_ACESS_PERMISSION=2;
    Button bt_scan;
    BroadcastReceiver receiver;
    ListView listView;
    TextView txt_receive;
    EditText txt_send;
    DeviceListAdapter deviceListAdapter;
    /*해당 디바이스에 접속하기 위해서는 소켓이 필요*/
    BluetoothSocket socket;/*대화용소켓*/
    String UUID = "50bb0c7c-f365-4c32-9fdb-5ce3cf480863";
    Thread connectThread;
    Handler handler;
    ClientThread clientThread;
    MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity= this;
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        /*이 툴바를 설정하자!! 즉 이시점부터 메뉴를 얹힌다거나, 네이게이션 버튼을 적용할수도 있는 시점*/
        setSupportActionBar(toolbar);

        bt_scan = (Button)findViewById(R.id.bt_scan);
        listView = (ListView)findViewById(R.id.listView);
        txt_receive = (TextView)findViewById(R.id.txt_receive);
        txt_send=(EditText)findViewById(R.id.txt_send);

        deviceListAdapter = new DeviceListAdapter(this);
        listView.setAdapter(deviceListAdapter);
        listView.setOnItemClickListener(this);

        checkSupportBluetooth();
        requestActiveBluetooth();

        handler = new Handler(){
            public void handleMessage(Message message) {
                /*서버가 보낸 메세지 반영하기*/
                Bundle bundle = message.getData();
                String msg = bundle.getString("msg");
                txt_receive.setText(msg);
            }
        };


        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                /*시스템이 방송하는 정보가 action으로 들어옴*/
                String action = intent.getAction();
                switch (action){
                    case BluetoothDevice.ACTION_FOUND:
                        BluetoothDevice bluetoothDevice =intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        String devicename = bluetoothDevice.getName();
                        Toast.makeText(getApplicationContext(), "발견"+devicename, Toast.LENGTH_SHORT).show();

                        /*5.어탭터의 arraylist에 디바이스 추가*/
                        Device dto = new Device();
                        dto.setName(devicename);
                        dto.setAddress(bluetoothDevice.getAddress());
                        dto.setBluetoothDevice(bluetoothDevice);

                        int count=0;

                        for (int i=0;i<deviceListAdapter.list.size();i++) {
                            Device device = deviceListAdapter.list.get(i);
                            if (device.getAddress().equals(dto.getAddress())){
                                count++;
                            }
                        }
                        if(count<1) {
                            deviceListAdapter.list.add(dto);
                        }
                        deviceListAdapter.notifyDataSetChanged();
                }
            }
        };

    }
    /*메뉴구성*/
    /*메뉴가 초기화 될때*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*메뉴XML 인플레이션 시키자!!*/
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*액션메뉴를 선택할때 호출되는 메서드*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "당신이 선택한 메뉴는"+item.getTitle(), Toast.LENGTH_SHORT).show();
        switch(item.getItemId()){
            case R.id.m1:;break;
            case R.id.m2:
                Intent intent = new Intent(this,MusicActivity.class);
                startActivity(intent);
                ;break;
        }

        return super.onOptionsItemSelected(item);
    }

    /*--------------------------------------------------------------------
    * 1.이 디바이스가 블루투스를 지원하는 지 여부 체크
    * --------------------------------------------------------------------*/
    public void checkSupportBluetooth(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter==null){
            showMsg("안내","이디바이스는 블루투스가 없다");
            this.finish();
        }
    }
    /*--------------------------------------------------------------------
    * 2.유저에게 활성화 요청
    * --------------------------------------------------------------------*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_BLUETOOTH_ENABLE:
                if(resultCode==RESULT_CANCELED){
                    showMsg("경고","앱을 사용하려면 블루투스 활성화 해야합니다.");
                    bt_scan.setEnabled(false);
                }
        }
    }

    public void requestActiveBluetooth(){
        Intent intent = new Intent();
        intent.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent,REQUEST_BLUETOOTH_ENABLE);
    }
    /*--------------------------------------------------------------------
    * 3.검색전 퍼미션 확인
    * --------------------------------------------------------------------*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ACESS_PERMISSION:
                if (requestCode > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    showMsg("안내", "위치에대한 퍼미션을 허락해주세요");
                }
        }
    }

    public void checkAccessPermission(){
        int acessPermmission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if(acessPermmission== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION
            },REQUEST_ACESS_PERMISSION);
        }else {
            scanDevice();
        }
    }
    /*--------------------------------------------------------------------
   * 4.검색
   * --------------------------------------------------------------------*/
    public void scanDevice(){
        /*시스템에게 디바이스 스캔을 요청하되, 시스템이 알려주는 여러 글로벌 이벤트 중에서 기기발견 이벤트만 골라받자
        (필터처리하자)*/
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);

        //브로드캐스트 리시버 등록시점
        registerReceiver(receiver,filter);
        bluetoothAdapter.startDiscovery();
    }


    public void btnClick(View view){
        switch(view.getId()){
            case R.id.bt_scan:
                checkAccessPermission();
                break;
            case R.id.bt_send:
                String msg = txt_send.getText().toString();
                Toast.makeText(mainActivity, msg, Toast.LENGTH_SHORT).show();
                clientThread.send(msg);
                break;
        }
    }

    /*--------------------------------------------------------------------
   * 5.선택한 디바이스에 접속!!
   * --------------------------------------------------------------------*/
    public void connectDevice(BluetoothDevice device){
        /*8.검색을 멈춘다 중요!!*/
        bluetoothAdapter.cancelDiscovery();
        /*브로드 캐스팅 그만 받기!!*/
        this.unregisterReceiver(receiver);

        try {
            socket=device.createInsecureRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID));

        } catch (IOException e) {
            e.printStackTrace();
        }


        connectThread = new Thread(){
            public void run() {
                try {
                    socket.connect();
                    clientThread = new ClientThread(mainActivity,socket);
                    clientThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        connectThread.start();

    }

    /*7원하는 디바이스를 선택한후, 그 디바이스를 이용하여 접속을 시도 하겟씀*/
    public void onItemClick(AdapterView<?> adapterView, View item, int index, long id) {
        TextView txt_adress = (TextView) item.findViewById(R.id.txt_adress);
        String address = txt_adress.getText().toString();

        for(int i=0;i<deviceListAdapter.list.size();i++){
            Device dto = deviceListAdapter.list.get(i);
            if(dto.getAddress().equals(address)){
                connectDevice(dto.getBluetoothDevice());
            }
        }

    }

    public void showMsg(String title,String msg){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title).setMessage(msg).show();
    }
}
