/*�������� ��û�� �õ��Ҽ��ִ� Ŭ���̾�Ʈ�� �����α׷��� (�ڹٽ�ũ��Ʈ,jsp) �����ϴٴ� ���������� ������!!!*/
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
	HttpURLConnection con;//��û �� ������ ó���� �� ����
	BufferedWriter buffw;
	BufferedReader buffr;
	
	public ClientMain(){
		try {
			/*url=new URL("http://localhost:9090/test?id=��ı&password=tiger");*/
			/*url=new URL("http://localhost:9090/test");*/
			url=new URL("http://www.screengolfnews.com/PEG/13647958237295.jpg");
			con=(HttpURLConnection)url.openConnection();
			
			//get��� ����!!
			con.setRequestMethod("GET");
			
			/*������� ����
			con.setRequestProperty("Accept-Language","ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4");
			con.setRequestProperty("Accept","text/json");*/
			
			//������ ����� ��Ʈ���� �̿��Ͽ�, �����͸� ����
			con.setDoOutput(true);//������ ���� ����
			con.setDoInput(true);//�о����
			/*con.getOutputStream();*/
			buffw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(),"utf-8"));
			buffw.write("id=��ı&password=tiger");
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
			System.out.println("������ ������"+sb.toString());
			
			
			
			int code = con.getResponseCode();//�����ڵ带 �޾ƿ��� ����
			
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
