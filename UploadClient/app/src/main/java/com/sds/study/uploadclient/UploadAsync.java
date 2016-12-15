package com.sds.study.uploadclient;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lee on 2016-12-15.
 */

public class UploadAsync extends AsyncTask<String, Void, String> {
    InputStream is; // 유저가 선택한 파일에 대한 입력 스트림!!!!
    URL url;
    HttpURLConnection con;
    String boundary;//웹서버에 요청시 파라미터간 구분을 위한 영역표시
    String newLine="\n\r";
    String TAG;
    MainActivity mainActivity;

    public UploadAsync(InputStream is,MainActivity mainActivity) {
        this.is = is;
        this.mainActivity = mainActivity;
    }

    protected void onPreExecute() {
        boundary =Long.toHexString(System.currentTimeMillis());
    }

    /*얘만 UI 제어 불가!!*/
    protected String doInBackground(String... params) {
        PrintWriter writer =null;
        OutputStream Out = null;
        try {
            url = new URL(params[0]);
            con = (HttpURLConnection) url.openConnection();

            //이연결로 부터 데이터를 입력 받을건지, 출력할건지....
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false); //캐쉬 사용않기
            con.setRequestProperty("Connection","keep-alive");
            con.setRequestProperty("Cache-Control","max-age=0");
            con.setRequestProperty("Content-type","multipart/form-data;boundary=" + boundary);

            //서버에 유저가 선택한 파일을 이 연결을 통해 전송한다....
            Out = con.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(Out,"UTF-8"));

            //바운더리명시
            writer.append("--" +boundary).append(newLine);//한줄생서
            writer.append("Content-Disposition:form-data; name=\"myfile\";filename=\""+params[1]+"\"").append(newLine);

            writer.append("Content-Type:"+HttpURLConnection.guessContentTypeFromName(params[1])).append(newLine);
            writer.append("Content-Transfer-Encoding:binary").append(newLine);
            writer.append(newLine).flush();
            //파일을 전송...
            byte[] b = new byte[1024];
            int flag = -1; //언제까지 읽어야 하는지에 대한 지표...

            while (true){
                flag=is.read(b);
                if(flag==-1)break;
                Out.write(b,0,flag);//읽어들인 데이터까지....
            }
            Out.flush();
            writer.append(newLine).flush();

            //마무리 바운더리...
            writer.append("--" + boundary + "--").append(newLine).flush();
            writer.close();

            con.getResponseCode();//요청이 일어나는 시점

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(Out!=null){
                try {
                    Out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(mainActivity,"완료",Toast.LENGTH_SHORT).show();
    }
}
