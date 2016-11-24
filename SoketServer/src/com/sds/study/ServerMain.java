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
	
	ServerSocket server; // ������ ������ ����
	int port=9999;
	Thread acceptThread;//�����ڸ� �����ϱ� ���� ������
	
	Vector<ServerThread> list = new Vector<ServerThread>(); //��Ƽ�� ������ ����Ʈ�� ���,, �� �������� ���带 ���� ������ȴ�
	
	public ServerMain() {
		tf = new JTextField(Integer.toString(port),10);
		bt = new JButton("����");
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
	
	/*1���� ����*/
	public void startServer(){
		try {
			server = new ServerSocket(port);
			area.append("��������\n");
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
	
	//2���������ϰ����ϴ� �ڵ�� run�� �־��!!
	public void run() {
		while(true){
			try {
				Socket socket=server.accept();//����ΰ� ������¿� ������!!
				String ip= socket.getInetAddress().getHostAddress();
				area.append(ip+"�����ڰ���\n");
				
				
				//4.���������忡 ���� ����
				ServerThread st = new ServerThread(this,socket);
				st.start();

				//5.��ܿ� ž�� ��Ƽ
				list.add(st);
				area.append(list.size()+"��������\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		new ServerMain();	
	}
	

}
