package com.yaoyaohao.study.nio.channels;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Demonstrate asynchronous connection of a SocketChannel.
 * 
 * @author liujianzhu
 * @date 2016年6月1日 下午4:29:00
 *
 */
public class SocketConnectAsyncTest {
	public static void main(String[] args) throws Exception{
		String host = "localhost";
		int port = 80;
		if(args.length == 2) {
			host = args[0];
			port = Integer.parseInt(args[1]);
		}
		InetSocketAddress addr = new InetSocketAddress(host, port);
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);
		
		System.out.println("initialing connection");
		sc.connect(addr);
		
		while(!sc.finishConnect()) {
			doSomethingUseful();
		}
		System.out.println("connection established");
		
		//Do something with the connected socket
		//The SocketChannel is still noblocking
		
		sc.close();
	}
	
	private static void doSomethingUseful(){
		System.out.println("doing something useless");
	}
}
