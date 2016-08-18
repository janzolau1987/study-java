package com.yaoyaohao.study.nio.reactor.multi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Multi测试 -- client
 * 
 * @author liujianzhu
 * @date 2016年8月17日 下午6:45:10
 */
public class MReactorClient {
	public static void main(String[] args) {
		try {
			InetSocketAddress addr = new InetSocketAddress("localhost", 9999);
			SocketChannel sc = SocketChannel.open();
			sc.connect(addr);
			if (sc.finishConnect()) { // 连接成功
				// 写
				String wstr = "你好，我是你的客户端.001";
				ByteBuffer wb = ByteBuffer.wrap(wstr.getBytes());
				sc.write(wb);
				sc.shutdownOutput();
				// 读
				ByteBuffer rb = ByteBuffer.allocate(1024);
				sc.read(rb);
				rb.flip();
				System.out.println("INFO >>> 收到服务端给的返回 ： " + new String(rb.array()));
			} else { // 连接失败
				System.out.println("连接失败.");
			}
			//
			sc.close();
		} catch (IOException ex) {
		}
	}
}
