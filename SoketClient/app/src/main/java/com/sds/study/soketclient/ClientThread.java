package com.sds.study.soketclient;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by lee on 2016-11-24.
 */

public class ClientThread extends Thread{
    MainActivity mainActivity;
    Socket socket;
    BufferedReader buffr;
    BufferedWriter buffw;
    String TAG;

    public ClientThread(MainActivity mainActivity, Socket socket) {
        this.mainActivity=mainActivity;
        this.socket=socket;
        TAG = this.getClass().getName();
        try {
            buffr = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
            buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void listen(){
        try {
            String msg = buffr.readLine();
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("msg",msg);
            message.setData(bundle);
            Log.d(TAG,"넘어온메세지"+msg);
            mainActivity.handler.sendMessage(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writer(String msg){
        try {
            buffw.write(msg);
            buffw.write("\n");
            buffw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void run() {
        while (true){
            listen();
        }
    }
}
