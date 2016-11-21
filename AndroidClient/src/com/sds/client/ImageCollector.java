/*�������� ��û�� �õ��Ҽ��ִ� Ŭ���̾�Ʈ�� �����α׷��� (�ڹٽ�ũ��Ʈ,jsp) �����ϴٴ� ���������� ������!!!*/
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
	HttpURLConnection con;//��û �� ������ ó���� �� ����
	InputStream is;
	FileOutputStream fos;
	
	public ImageCollector(){
		try {
	
			url=new URL("http://www.screengolfnews.com/PEG/13647958237295.jpg");
			con=(HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setDoInput(true);//�о����
			
			//�̹����� �������� ���� ����� �ƴϹǷ�, ���ڱ�� ��Ʈ������ ��ȯ ó���� �ʿ����
			is = con.getInputStream();
			fos= new FileOutputStream("C:/android_workspace/AndroidClient/download/test.jpg");
			//�̹����� �۴������ �迭
			byte[]b = new byte[1024];
			int data=-1;
			while(true){
				data=is.read(b);
				if(data==-1)break;
				fos.write(b);
			}
			System.out.println("��ġ��Ϸ�");
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
