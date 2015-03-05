package com.tk.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 功能:客户端线程类
 * 描述:
 * @author jinhuatao
 * @date 2015-3-5 上午11:11:29
 */
public class ClientThread extends Thread{
	Socket socket;				//Socket对象
	BufferedReader is;			//输入流
	PrintWriter os;				//输出流
	
	/**
	 * 启动客户端 连接服务器，同时发送用户名
	 * @param username
	 */
	public ClientThread(String username) {
		try {
			//连接服务器
			socket = new Socket("localhost",12345);
			
			//输入输出流
			is = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			os = new PrintWriter(
					socket.getOutputStream());
			
			//发送用户名
			os.println(username);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 循环读取服务端转法的消息
	 * @author jinhuatao
	 * @date 2015-3-5上午11:20:56
	 */
	@Override
	public void run() {
		try {
			String readline;
			while((readline = is.readLine()) != null){
				System.out.println(readline);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送消息
	 * @author jinhuatao
	 * @date 2015-3-5上午11:23:01
	 * @param message void
	 */
	public void send(String message){
		os.println(message);
		os.flush();
	}
	
	/**
	 * 关闭客户端
	 * @author jinhuatao
	 * @date 2015-3-5上午11:24:02 void
	 */
	public void close(){
		try {
			//关闭输入输出流
			is.close();
			os.close();
			
			//关闭客户端
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
