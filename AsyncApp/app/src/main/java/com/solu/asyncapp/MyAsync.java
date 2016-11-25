package com.solu.asyncapp;

/*
 * AsyncTask 의 제너릭 타입의 용도
  인수1 : 이 객체를 이용하여 비동기 업무를 수행할때 전달하고 싶은 매개변수
             자료형을 기재한다.
  인수2 : 비동기 업무 진행중 필요한 자료형을 매개변수로 기재..
  인수3 : 비동기 업무가 완료된 후, 필요한 자료형이 있다면 매개변수로 기재
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyAsync extends AsyncTask<String,Void, String>{
    /*
    * doInbackground 작업전에 준비해야할 업무가 있다면 여기서 처리..
    * main thread에 의해 호출된다. 따라서 UI제어가 가능하다...
    *
    * */
    String TAG;


    public MyAsync() {
        TAG=this.getClass().getName();
    }

    protected void onPreExecute() {
        Log.d(TAG, "onPreExecute called");
    }

    /*제너릭의 인수1이 여기에 쓰임..
        비동기 스레드 업무를 처리하는 메서드...
        UI를 제어할 수 없다!!
        만일 업무가 웹프로그램 이라면, 서버측의 요청은 이 메서드로 구현해야함
        가변형 인자있는 메서드를 사용한다..
    */
    protected String doInBackground(String... params) {
        //Log.d(TAG, params[0]);
        BufferedReader buffr=null;
        StringBuffer sb = new StringBuffer();
        try {
            URL url=new URL(params[0]);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true); //InputStream 사용전 명시!!
            int code=con.getResponseCode();//요청이 일어남!!
            InputStream is=con.getInputStream();
            buffr=new BufferedReader(new InputStreamReader(is, "utf-8"));

            String data=null;
            while((data=buffr.readLine()) !=null){
                sb.append(data);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(buffr!=null){
                try {
                    buffr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /*인수2가 여기에 쓰임...
    * main thread에 의해 호출된다. 따라서 UI제어가 가능하다...
    * */
    protected void onProgressUpdate(Void... values) {
        Log.d(TAG, "onProgressUpdate called");
    }

    /* 인수3이 여기에 쓰임
    * 비동기 업무가 종료될때 호출된다.
    * main thread에 의해 호출된다. 따라서 UI제어가 가능하다...
    * */
    protected void onPostExecute(String s) {
        /*제이슨 파싱 후, mainActivity의 listView에 반영*/


        Log.d(TAG, "onPostExecute called"+s);
        super.onPostExecute(s);
    }
}















