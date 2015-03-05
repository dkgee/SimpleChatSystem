package com.tk.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 功能:聊天客户端
 * 描述:
 * @author jinhuatao
 * @date 2015-3-5 上午11:10:21
 */
public class ChatClient {
	public static void main(String[] args) {
		try {
			ClientThread client = new ClientThread(args[0]);
			client.start();
			
			//输入输出流
			BufferedReader sin = new BufferedReader(
					new InputStreamReader(System.in));
			
			//循环读取键盘输入
			String readline;
			while((readline = sin.readLine()) != null){
				if(readline.equals("bye")){
					client.close();
					System.exit(0);
				}
				client.send(readline);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
