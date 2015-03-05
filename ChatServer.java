package com.tk.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

/**
 * 功能:聊天服务端
 * 描述:
 * @author jinhuatao
 * @date 2015-3-5 上午11:51:57
 */
public class ChatServer {
	public static void main(String[] args) {
		ServerSocket server = null;
		try{
			//启动服务端
			server = new ServerSocket(12345);
			System.out.println("Server start:"
					+server.getInetAddress().getHostAddress()+":"
					+server.getLocalPort());
			
			//客户端列表
			Hashtable<String, ServerThread> clientlist = 
					new Hashtable<String, ServerThread>();
			
			//监听客户端
			while(true){
				Socket socket = server.accept();
				//启动客户端处理线程
				new ServerThread(socket, clientlist).start();
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
