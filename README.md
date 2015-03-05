package com.tk.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;

/**
 * 功能:服务端线程
 * 描述:
 * @author jinhuatao
 * @date 2015-3-5 上午11:35:00
 */
public class ServerThread extends Thread{
	Socket socket;					//Socket对象
	BufferedReader is;				//输入流
	PrintWriter os;					//输出流
	Hashtable<String, ServerThread> clientlist;		//客户端列表
	String username;				//用户名
	
	/**
	 * 服务端构造函数
	 */
	public ServerThread(Socket socket,
			Hashtable<String, ServerThread> clientlist) {
		this.socket = socket;
		this.clientlist = clientlist;
		
		//输出输出流
		try{
			is = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			os = new PrintWriter(socket.getOutputStream());
			
			//读取用户名
			this.username = is.readLine();
			clientlist.put(username, this);
			
			//显示连接信息
			System.out.println(username+" connet:"
					+socket.getInetAddress().getHostAddress()+":"
					+socket.getPort());
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author jinhuatao
	 * @date 2015-3-5上午11:43:17
	 */
	@Override
	public void run() {
		try{
			//循环处理客户端输入
			String line;
			while((line = is.readLine()) != null){
				//转发用户输入信息
				System.out.println(username +" send to "+line);
				if(line.equals("bye")){
					break;
				}else{
					//转发消息给客户端
					String[] arr = line.split(":");
					if(arr.length == 2){
						if(clientlist.containsKey(arr[0])){
							clientlist.get(arr[0])
								.send(username+":"+arr[1]);
						}
					}
				}
			}
			
			//关闭输入输出流
			is.close();
			os.close();
			
			//关闭客户端
			socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送消息
	 * @author jinhuatao
	 * @date 2015-3-5上午11:51:10
	 * @param message void
	 */
	public void send(String message){
		System.out.println("Send to "+username+" "+message);
		os.println(message);
		os.flush();
	}
}
