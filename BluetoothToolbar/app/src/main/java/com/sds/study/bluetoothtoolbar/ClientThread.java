package com.sds.study.bluetoothtoolbar;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by lee on 2016-11-23.
 */

public class ClientThread extends Thread {
    MainActivity mainActivity;
    BluetoothSocket socket;
    BufferedWriter buffw;
    BufferedReader buffr;
    boolean flag = true;
    /*안드로이드에서는 handler는 개발자 정의 쓰레드가 보유할수 없다*/

    public ClientThread(MainActivity mainActivity, BluetoothSocket socket) {
        this.mainActivity = mainActivity;
        this.socket = socket;

        try {
            buffr = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
            buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*듣는 메서드*/
    public void listen(){
        try {
            String msg = buffr.readLine();
            /*서버의 메세지를 textview에 간접 반영하자!!*/
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("msg",msg);
            message.setData(bundle);
            mainActivity.handler.sendMessage(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*말거는 메서드*/
    public void send(String msg){
        try {
            buffw.write(msg);
            buffw.write("\n");
            buffw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*독립적으로 수행하고 싶은 코드가 있다면 run에 넣어라!!*/
    public void run() {
        while (flag){
            listen();
        }
    }
}
