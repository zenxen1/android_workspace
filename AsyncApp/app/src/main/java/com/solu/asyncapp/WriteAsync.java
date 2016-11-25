package com.solu.asyncapp;

import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WriteAsync extends AsyncTask<String, Void , String>{
    URL url;
    HttpURLConnection con;

    protected void onPreExecute() {
        try {
            url = new URL("http://192.168.0.35:9090/member/regist.jsp");
            con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String doInBackground(String... strings){
        BufferedWriter buffw=null;

        try {
            OutputStream out=con.getOutputStream();
            buffw = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));
            buffw.write("name=레몬");
            buffw.write("\n");
            buffw.flush();

            int code=con.getResponseCode(); //요청 일어남

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
