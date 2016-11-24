package com.sds.study;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/*3Ŭ���̾�Ʈ�� �޼����� ���� ������ ���鼭 
 * �ް�, �ٽ� ������ ���� ��ȭ�� ������...*/
public class ServerThread extends Thread {
	ServerMain serverMain;
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	boolean flag = true;

	public ServerThread(ServerMain serverMain,Socket socket) {
		this.serverMain=serverMain;
		this.socket = socket;
		
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listen() {
		try {
			String msg = buffr.readLine();
			serverMain.area.append(msg+"\n");
			//6.��ܿ� ž��� ����鿡�� ���� �� ������
			for(int i=0;i<serverMain.list.size();i++){
				ServerThread st = serverMain.list.get(i);
				st.send(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(String msg) {
		try {
			buffw.write(msg);
			buffw.write("\n");
			buffw.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (flag) {
			listen();
		}
	}
}
