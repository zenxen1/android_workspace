package com.sds.study.toobarserver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter;
    static final int REQUEST_BLUETOOTH_ENABLE = 1;
    static final int REQUEST_DISCOVERABLE=2;
    TextView txt_status;
    /*클라이언트는 이 UUID를 통해서 나이 서버로 접속 하면 된다.*/
    String UUID="e0e34537-2e80-4aa9-9d71-c3affb50282e";
    /*클라이언트의 접속을 받을 수 있는 서버(소켓서버와 상당히 비슷)*/
    BluetoothServerSocket server;
    String serviceName;
    Thread acceptThread;/*접속자를 받기위한 쓰레드*/
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_status = (TextView)findViewById(R.id.txt_status);

        handler = new Handler(){
            /*7. UI제어가 가능하다 왜 main Thread에서 호출*/
            public void handleMessage(Message message) {
                Bundle bundle =message.getData();
                String msg = bundle.getString("msg");
                txt_status.append(msg);
            }
        };

        checkSupportBluetooth();
        requestActiveBluetooth();
        requestDiscoverable();
        acceptDevice();

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
                }break;
            case REQUEST_DISCOVERABLE:
                if(resultCode==RESULT_CANCELED){
                    showMsg("경고","발견되고싶지않냐? 발견되게 해주라~");
                }
        }
    }

    public void requestActiveBluetooth(){
        Intent intent = new Intent();
        intent.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent,REQUEST_BLUETOOTH_ENABLE);
    }


    /*4.접속자 받을 준비*/
    public void acceptDevice(){
        serviceName = this.getPackageName();
        try {
            server = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(serviceName, java.util.UUID.fromString(UUID));
        } catch (IOException e) {
            e.printStackTrace();
        }
        acceptThread = new Thread(){
            public void run(){
                try {
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("msg","서버준비됨\n");
                    message.setData(bundle);
                    handler.sendMessage(message);

                    BluetoothSocket socket = server.accept();

                    Message message2 = new Message();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("msg","접속자감지\n");
                    message2.setData(bundle2);
                    handler.sendMessage(message2);

                    ServerThread st = new ServerThread(socket);
                    //st.start();
                    /*더이상 접속자 허용 방지*/
                    server.close();//서버프로그램을 중단하는것이아니라 접속자의 접속을 원천 차단이 목적임 NO 오해!
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        acceptThread.start();
    }
    /*클라이언트가 서버인 나를 발견할 수있도록 검색 허용 옵션을 지정하자..*/
    public void requestDiscoverable(){
        Intent intent = new Intent();
        intent.setAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,60*3);
        startActivityForResult(intent,REQUEST_DISCOVERABLE);
    }
    /*5.대화나누기*/

    public void showMsg(String title,String msg){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title).setMessage(msg).show();
    }
}
