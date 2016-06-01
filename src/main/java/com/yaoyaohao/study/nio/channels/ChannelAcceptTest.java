package com.yaoyaohao.study.nio.channels;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Test nonblocking accept() using ServerSocketChannel.
 * Start this program,then "telnet localost 1234" to connect to it
 * 
 * @author liujianzhu
 * @date 2016年6月1日 下午3:37:17
 *
 */
public class ChannelAcceptTest {
	public static final String GREETING = "Hello I must be going.\r\n";

	public static void main(String[] args) throws Exception{
		int port = 1234;
		if(args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.socket().bind(new InetSocketAddress(port));
		ssc.configureBlocking(false);
		while(true) {
			System.out.println("Waiting for connections.");
			SocketChannel sc = ssc.accept();
			if(sc == null) {
				//no connections,snooze a while
				Thread.sleep(2000);
			}
			else{
				System.out.println("Incoming connection from : " + sc.socket().getRemoteSocketAddress());
				buffer.rewind();
				sc.write(buffer);
				sc.close();
			}
		}
	}

}
