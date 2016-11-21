/*웹서버에 요청을 시도할수있는 클라이언트는 웹프로그램만 (자바스크립트,jsp) 가능하다느 고정관념을 버리자!!!*/
package com.sds.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageCollector {
	URL url;
	HttpURLConnection con;//요청 및 응답을 처리할 수 있음
	InputStream is;
	FileOutputStream fos;
	
	public ImageCollector(){
		try {
	
			url=new URL("http://www.screengolfnews.com/PEG/13647958237295.jpg");
			con=(HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setDoInput(true);//읽어오기
			
			//이미지는 육안으로 읽을 대상이 아니므로, 문자기반 스트림까지 변환 처리할 필요없다
			is = con.getInputStream();
			fos= new FileOutputStream("C:/android_workspace/AndroidClient/download/test.jpg");
			//이미지를 퍼담기위한 배열
			byte[]b = new byte[1024];
			int data=-1;
			while(true){
				data=is.read(b);
				if(data==-1)break;
				fos.write(b);
			}
			System.out.println("훔치기완료");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	public static void main(String[] args) {
		new ImageCollector();
	}

}
