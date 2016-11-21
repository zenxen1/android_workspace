package com.sds.study.webconnectapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    URL url;
    HttpURLConnection con;
    BufferedWriter buffw;
    BufferedReader buffr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void request(){
        try {
            url=new URL("http://70.12.112.94:9090/test");
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            con.setDoOutput(true);
            con.setDoInput(true);

            buffw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(),"utf-8"));
            buffw.write("id=스캇&password=tiger");
            buffw.write("\n");
            buffw.flush();

            buffr= new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
            StringBuffer sb = new StringBuffer();
            String data=null;
            while(true){
                data = buffr.readLine();
                if(data==null)break;
                sb.append(data);
            }
            System.out.println("서버의 응답결과"+sb.toString());



            int code = con.getResponseCode();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void btnClick(View view){

    }
}
