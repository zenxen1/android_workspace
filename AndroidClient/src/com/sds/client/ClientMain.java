/*웹서버에 요청을 시도할수있는 클라이언트는 웹프로그램만 (자바스크립트,jsp) 가능하다느 고정관념을 버리자!!!*/
package com.sds.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClientMain {
	URL url;
	HttpURLConnection con;//요청 및 응답을 처리할 수 있음
	BufferedWriter buffw;
	BufferedReader buffr;
	
	public ClientMain(){
		try {
			/*url=new URL("http://localhost:9090/test?id=스캇&password=tiger");*/
			/*url=new URL("http://localhost:9090/test");*/
			url=new URL("http://www.screengolfnews.com/PEG/13647958237295.jpg");
			con=(HttpURLConnection)url.openConnection();
			
			//get방식 선언!!
			con.setRequestMethod("GET");
			
			/*헤더정보 셋팅
			con.setRequestProperty("Accept-Language","ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4");
			con.setRequestProperty("Accept","text/json");*/
			
			//서버와 연결된 스트림을 이용하여, 데이터를 전송
			con.setDoOutput(true);//전송을 위한 셋팅
			con.setDoInput(true);//읽어오기
			/*con.getOutputStream();*/
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
			
			
			
			int code = con.getResponseCode();//응답코드를 받아오는 시점
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ClientMain();
	}

}
