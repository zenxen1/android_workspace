package com.sds.study;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerMain extends JFrame implements ActionListener, Runnable{
	JTextField tf;
	JButton bt;
	JTextArea area;
	JScrollPane scroll;
	
	ServerSocket server; // 접속자 감지용 소켓
	int port=9999;
	Thread acceptThread;//접속자를 감지하기 위한 쓰레드
	
	Vector<ServerThread> list = new Vector<ServerThread>(); //멀티는 소켓을 리스트에 담고,, 각 스레드의 샌드를 포문 돌리면된다
	
	public ServerMain() {
		tf = new JTextField(Integer.toString(port),10);
		bt = new JButton("접속");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		
		setLayout(new FlowLayout());
		
		
		scroll.setPreferredSize(new Dimension(250, 300));
		
		add(tf);
		add(bt);
		add(scroll);
		
		bt.addActionListener(this);
		
		setVisible(true);
		setSize(300, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/*1서버 가동*/
	public void startServer(){
		try {
			server = new ServerSocket(port);
			area.append("서버가동\n");
			acceptThread = new Thread(this);
			acceptThread.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		startServer();
	}
	
	//2독립수행하고자하는 코드는 run에 넣어라!!
	public void run() {
		while(true){
			try {
				Socket socket=server.accept();//실행부가 블락상태에 빠진다!!
				String ip= socket.getInetAddress().getHostAddress();
				area.append(ip+"접속자감지\n");
				
				
				//4.서버스레드에 소켓 전달
				ServerThread st = new ServerThread(this,socket);
				st.start();

				//5.명단에 탑재 멀티
				list.add(st);
				area.append(list.size()+"명접속함\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		new ServerMain();	
	}
	

}
